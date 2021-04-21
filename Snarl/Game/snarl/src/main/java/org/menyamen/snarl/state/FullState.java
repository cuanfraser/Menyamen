
package org.menyamen.snarl.state;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import java.awt.Point;

import org.menyamen.snarl.characters.Adversary;
import org.menyamen.snarl.characters.RemoteAdversary;
import org.menyamen.snarl.characters.Ghost;
import org.menyamen.snarl.characters.Player;
import org.menyamen.snarl.characters.Zombie;
import org.menyamen.snarl.constraints.CharacterEnum;
import org.menyamen.snarl.constraints.Move;
import org.menyamen.snarl.constraints.MoveResult;
import org.menyamen.snarl.layout.Level;
import org.menyamen.snarl.tiles.Door;
import org.menyamen.snarl.tiles.Tile;
import org.menyamen.snarl.tiles.Wall;

public class FullState {
    int currentLevel = 0;
    List<Level> levels;
    List<Player> players;
    List<Adversary> adversaries;
    List<RemoteAdversary> remoteAdversaries;
    List<RemoteAdversary> placedRemoteAdversaries;

    public FullState(Level level) {
        this.levels = new ArrayList<Level>();
        this.levels.add(level);
        this.players = new ArrayList<Player>();
        this.adversaries = new ArrayList<Adversary>();
        this.remoteAdversaries = new ArrayList<RemoteAdversary>();
    }

    public FullState(int currentLevel, List<Level> levels) {
        this.currentLevel = currentLevel;
        this.levels = levels;
        this.players = new ArrayList<Player>();
        this.remoteAdversaries = new ArrayList<RemoteAdversary>();
    }

    // Intermediate Game State
    public FullState(int currentLevel, List<Level> levels, List<Player> players, List<Adversary> adversaries) {
        this.currentLevel = currentLevel;
        this.levels = levels;
        this.players = players;
        this.adversaries = adversaries;
    }

    // Intermediate Game State for 1 Level
    public FullState(Level level, List<Player> players, List<Adversary> adversaries, Boolean exitLocked) {
        this.levels = new ArrayList<Level>();
        level.setExitLocked(exitLocked);
        level.addPlayers(players);
        level.addAdversaries(adversaries);
        this.levels.add(level);
        this.players = players;
        this.adversaries = adversaries;
    }

    /**
     * Moves Player specified by name to Move destination.
     * 
     * @param name            String name of Player to attempt to move.
     * @param destinationMove Move to attempt with Player.
     * @return MoveResult.
     */
    public MoveResult move(String name, Move destinationMove) {

        int playerIndex = -1;
        // Find Player, Check if other Player occupies Point
        for (int i = 0; i < players.size(); i++) {
            Player currentPlayer = players.get(i);
            if (currentPlayer.getName().equals(name)) {
                playerIndex = i;
            }
        }

        // Player not found
        if (playerIndex == -1) {
            return MoveResult.INVALID;
        }

        Player player = players.get(playerIndex);
        Point point = destinationMove.getDestination();

        if (destinationMove.getStayStill()) {
            point = player.getPos();
        }

        // Check if other Player occupies Point in State (DOUBLE POS SITUATION RN)
        // TODO: DOUBLE POS CHANGE
        for (int i = 0; i < players.size(); i++) {
            Player currentPlayer = players.get(i);
            if (currentPlayer.getName().equals(name)) {
                continue;
            }
            // Player occupied tile
            else if (currentPlayer.getPos().equals(point)) {
                return MoveResult.NOTTRAVERSABLE;
            }
        }
        // Adversaries
        for (Adversary currentAdv : adversaries) {
            if (currentAdv.getPos().equals(point)) {
                player.setIsExpelled(true);
                return MoveResult.EJECTED;
            }
        }

        return levels.get(currentLevel).movePlayer(player, destinationMove);

    }

    public Player moveRemoteAdversary(RemoteAdversary adversary, Move destinationMove) {
        
        Point point = destinationMove.getDestination();

        if (destinationMove.getStayStill()) {
            point = adversary.getPos();
        }
        Level level = getCurrentLevel();
        if (level.isOccupiedBy(point) == CharacterEnum.PLAYER) {
            // The player needs to be removed
            Player player = level.getPlayer(point);
            player.setIsExpelled(true);
            Tile tile = level.getTile(point);
            //move the adversary to players position
            level.getTile(adversary.getPos()).setAdversary(null);
            adversary.setPos(point);
            tile.setAdversary(adversary);
            //expelled player is returned otherwise null
            return player;
          
        }
        
        Tile tile = level.getTile(point);
        if (tile instanceof Door || tile instanceof Wall) {
              //Move ghost to a random room and zombie cannot move
            if (adversary.getType() == "ghost") {
                List<Adversary> list = new ArrayList<Adversary>();
                list.add(adversary);
                level.randomAdversariesPlacement(list);
            }
        }
        else{
            //set new adversary position 
            level.getTile(adversary.getPos()).setAdversary(null);
            adversary.setPos(point);
            tile.setAdversary(adversary);
     
           
        }
       return null;

    }

    //Filtering out the players that are set as expelled to ensure that adversaries always have an up to date list of the players active in a level
    private List<Player> getActivePlayers() {
        return players.stream().filter(player -> !player.getIsExpelled()).collect(Collectors.toList());
    }

    /**
     * Moves Adversary specified by the Adversary type (Zombie or Ghost)
     * 
     * @param adversary Adversary that attempts to move
     * @return Player that is expelled (if no player is expelled return null).
     */
    public Player moveAdversary(Adversary adversary) throws IllegalArgumentException {
        Level level = getCurrentLevel();

        // Possible cardinal moves, one move away from Adversary, setting the includeWall true for ghosts 
        List<Point> possibleMoves = level.cardinalMove(adversary.getPos(), 1, true);
        //Remove the adversaries own position
        possibleMoves.remove(adversary.getPos());
        //keeps track of the subset moves that are traversable
        List<Point> availableMoves = new ArrayList<Point>(possibleMoves);
        //FIRST LOOP
        // look in the allowable moves (1 cardinal move away in any direction) and if a player is found, 
        // move the adversary there
        for (Point possiblePosition : possibleMoves) {
            if (level.isOccupiedBy(possiblePosition) == CharacterEnum.PLAYER) {
                // The player needs to be removed
                Player player = level.getPlayer(possiblePosition);
                player.setIsExpelled(true);
                Tile tile = level.getTile(possiblePosition);
                //move the adversary to players position
                level.getTile(adversary.getPos()).setAdversary(null);
                adversary.setPos(possiblePosition);
                tile.setAdversary(adversary);
                //expelled player is returned otherwise null
                return player;
              //Removes other adversary position from available moves
            } else if (level.isOccupiedBy(possiblePosition) == CharacterEnum.ADVERSARY) {
                availableMoves.remove(possiblePosition);
            }
            //If the adversary is a zombie then remove doors and walls from available moves 
            Tile tile = level.getTile(possiblePosition);
            if (tile instanceof Door || tile instanceof Wall && adversary.getType() == "zombie") {
                availableMoves.remove(possiblePosition);
            }
        }
        
        //If we have only one available move, then move the adversary there and return
        if(availableMoves.size() == 1){
            Point availablePos = availableMoves.get(0);
            Tile tile = level.getTile(availablePos);
            if (tile instanceof Door || tile instanceof Wall) {
                  //Move ghost to a random room and zombie cannot move
                  //Todo instead of random room, find the room nearest to the player
                if (adversary.getType() == "ghost") {
                    List<Adversary> list = new ArrayList<Adversary>();
                    list.add(adversary);
                    level.randomAdversariesPlacement(list);
                }
                 //No player was expelled
                 return null;
            }
            else{
                level.getTile(adversary.getPos()).setAdversary(null);
                adversary.setPos(availablePos);
                tile.setAdversary(adversary);
                //No player was expelled
                return null;
            }
        }
       
        //Get all active players positions (TO-DO: in this particular room) by getPos
        List<Point> playersPositions = getActivePlayers().stream().map(player -> player.getPos())
                .collect(Collectors.toList());

        //If there are players in the list use the nearest player function that utilizes the distance formula to find the closest player
        if (!playersPositions.isEmpty()) {
            Point availablePos = findNearestPlayerPoint(playersPositions, availableMoves);
            //If there is a player nearby then move the adversary to the nearest avaiable position
            if (availablePos != null) {
                level.getTile(adversary.getPos()).setAdversary(null);
                adversary.setPos(availablePos);
                Tile tile = level.getTile(availablePos);
                tile.setAdversary(adversary);
                //No player was expelled
                return null;
            }
        }

        return null;
    }

     /**
     * Finding the nearest available position from the adversary to a player
     * 
     * @param playersPositions List of all the players positions as Points
     * @param availablePositions List of all the available moves for the adversary 
     * @return Player that is expelled (if no player is expelled return null).
     */
    private Point findNearestPlayerPoint( List<Point> playersPositions,  List<Point> availablePositions) {
        Point nearestPosition = null;
        Double nearestDistance = null;
        
       for ( Point p : availablePositions) {
           for ( Point pl : playersPositions) {
                double distance = calculateDistanceBetweenPoints(p, pl);
                if(nearestDistance != null && nearestDistance < distance) {
                    continue;
                }
                else {
                     nearestPosition = p;
                     nearestDistance = distance;
                }
            }
        }
        return nearestPosition;
   }


    //distance formula
    public double calculateDistanceBetweenPoints(Point a, Point b) {
        return Math.sqrt((b.y - a.y) * (b.y - a.y) + (b.x - a.x) * (b.x - a.x));
    }

    public PlayerState makePlayerState(Player player) {
        List<Tile> grid = levels.get(currentLevel).viewable(player.getPos());

        List<Player> viewablePlayers = new ArrayList<Player>();
        List<Adversary> viewableAdvers = new ArrayList<Adversary>();

        for (Tile tile : grid) {
            if (tile == null) {
                continue;
            }
            Point currentPos = tile.getPos();
            for (Player currentPlayer : this.players) {
                Point playerPos = currentPlayer.getPos();
                if (playerPos.equals(currentPos)) {
                    viewablePlayers.add(currentPlayer);
                }
            }
            for (Adversary currentAdversary : this.adversaries) {
                Point advPos = currentAdversary.getPos();
                if (advPos.equals(currentPos)) {
                    viewableAdvers.add(currentAdversary);
                }
            }
        }

        PlayerState output = new PlayerState(player, grid, viewablePlayers, viewableAdvers);
        return output;
    }

    // renders to console the current state of the level
    public String print() {
        return this.levels.get(currentLevel).print();
    }

    // Getters & Setters
    public List<Player> getPlayers() {
        return this.players;
    }

    public List<Adversary> getAdversaries() {
        return this.adversaries;
    }

    public List<RemoteAdversary> getRemoteAdversaries() {
        return this.remoteAdversaries;
    }

    public List<RemoteAdversary> getPlacedRemoteAdversaries() {
        return this.placedRemoteAdversaries;
    }

    public Boolean getExitLocked() {
        return this.levels.get(0).getExitLocked();
    }

    public Level getCurrentLevel() {
        return this.levels.get(currentLevel);
    }

    public int getCurrentLevelIndex() {
        return this.currentLevel;
    }

    public boolean nextLevel() {
        currentLevel++;
        if (currentLevel >= levels.size()) {
            return false;
        }

        initialiseLevel();

        return true;

    }

    public void initialiseLevel() {
        //Depending on the rule of placement, not all remote adversaries registered may get placed.
        placedRemoteAdversaries = new ArrayList<RemoteAdversary>();
        // New Adversaries
        adversaries = new ArrayList<Adversary>();

        int levelNumber = currentLevel + 1;
        
        List<RemoteAdversary> remoteZombies = remoteAdversaries.stream().filter(r -> r.getType().equals("zombie")).collect(Collectors.toList());
        List<RemoteAdversary> remoteGhosts = remoteAdversaries.stream().filter(r -> r.getType().equals("ghost")).collect(Collectors.toList());

        //zombie placement
        int zombieCount = Math.floorDiv(levelNumber, 2) + 1;
        int remoteZombiesCount = remoteZombies != null ? remoteZombies.size() : 0;
        if(zombieCount > 0){
            if(remoteZombiesCount > zombieCount){
                placedRemoteAdversaries.addAll(remoteZombies.subList(0, zombieCount));
            }
            else
            {
                //remote adversaries take the place of local adversaries
                if(remoteZombiesCount > 0)
                    placedRemoteAdversaries.addAll(remoteZombies);
                zombieCount -= remoteZombiesCount;
                for (int i = 0; i < zombieCount; i++) {
                    Adversary zombie = new Zombie("Zombie" + currentLevel + i);
                    adversaries.add(zombie);
                }
            }
        }
 
        //ghost placement
        int ghostCount = Math.floorDiv(levelNumber - 1, 2);
        int remoteGhostsCount = remoteGhosts!= null ? remoteGhosts.size() : 0;
        if(ghostCount > 0){
            if(remoteGhostsCount > ghostCount){
                placedRemoteAdversaries.addAll(remoteGhosts.subList(0, ghostCount));
            }
            else
            {
                if(remoteGhostsCount > 0)
                    placedRemoteAdversaries.addAll(remoteGhosts);
                    ghostCount -= remoteGhostsCount;
                    for (int i = 0; i < ghostCount; i++) {
                        Adversary ghost = new Ghost("Ghost" + currentLevel + i);
                        adversaries.add(ghost);
                    }
            }
        }
        // randomly place both local and remote adversaries
        levels.get(currentLevel).randomAdversariesPlacement(adversaries, placedRemoteAdversaries);

        // Players
        for (Player player : this.players) {
            player.setIsExpelled(false);
        }

        players = levels.get(currentLevel).randomPlayersPlacement(players);
    }

}
