package org.menyamen.snarl.state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import java.awt.Point;

import org.menyamen.snarl.characters.Adversary;
import org.menyamen.snarl.characters.Ghost;
import org.menyamen.snarl.characters.Player;
import org.menyamen.snarl.characters.Zombie;
import org.menyamen.snarl.constraints.CharacterEnum;
import org.menyamen.snarl.constraints.Move;
import org.menyamen.snarl.constraints.MoveResult;
import org.menyamen.snarl.gameobjects.GameObject;
import org.menyamen.snarl.gameobjects.GameObject.GameObjectType;
import org.menyamen.snarl.layout.Level;
import org.menyamen.snarl.tiles.Door;
import org.menyamen.snarl.tiles.Tile;
import org.menyamen.snarl.tiles.Wall;

public class FullState {
    int currentLevel = 0;
    List<Level> levels;
    List<Player> players;
    List<Adversary> adversaries;

    public FullState(Level level) {
        this.levels = new ArrayList<Level>();
        this.levels.add(level);
        this.players = new ArrayList<Player>();
        this.adversaries = new ArrayList<Adversary>();
    }

    public FullState(int currentLevel, List<Level> levels) {
        this.currentLevel = currentLevel;
        this.levels = levels;
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

        // Check if other Player occupies Point
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

        // Not traversable point
        if (!levels.get(currentLevel).isTraversable(point)) {
            return MoveResult.NOTTRAVERSABLE;
        }
        // Adversaries
        for (Adversary currentAdv : adversaries) {
            if (currentAdv.getPos().equals(point)) {
                player.setIsExpelled(true);
                return MoveResult.EJECTED;
            }
        }

        player.setPos(point);

        GameObject object = levels.get(currentLevel).getObject(point);
        if (object != null) {
            if (object.getType() == GameObjectType.EXIT) {
                if (!levels.get(currentLevel).getExitLocked()) {
                    players.remove(playerIndex);
                    return MoveResult.EXIT;
                } else {
                    return MoveResult.SUCCESS;
                }
            } else if (object.getType() == GameObjectType.KEY) {
                levels.get(currentLevel).setExitLocked(false);
                levels.get(currentLevel).getTile(point).setGameObject(null);
                return MoveResult.KEY;
            }
        }

        return MoveResult.SUCCESS;

    }

    private List<Player> getActivePlayers() {
        return players.stream().filter(player -> !player.getIsExpelled()).collect(Collectors.toList());
    }

    public void moveAdversary(Adversary adversary) throws IllegalArgumentException {
        Level level = getCurrentLevel();

        // Possible cardinal moves, one move away from Adversary
        List<Point> possibleMoves = level.cardinalMove(adversary.getPos(), 1);
        List<Point> availableMoves = new ArrayList<Point>(possibleMoves);

        // look in the allowable moves and if a player is found, move the adversary
        // there
        for (Point possiblePosition : possibleMoves) {
            if (level.isOccupiedBy(possiblePosition) == CharacterEnum.PLAYER) {
                Player player = level.getPlayer(possiblePosition);

                // The player needs to be removed
                player.setIsExpelled(true);
                // is the players list per level
                players.remove(player);
                Tile tile = level.getTile(possiblePosition);

                adversary.setPos(possiblePosition);
                tile.setAdversary(adversary);
                return;
            } else if (level.isOccupiedBy(possiblePosition) == CharacterEnum.ADVERSARY) {
                availableMoves.remove(possiblePosition);
            }
        }

        List<Point> playersPositions = getActivePlayers().stream().map(player -> player.getPos())
                .collect(Collectors.toList());

        if (!playersPositions.isEmpty()) {
            Point availablePos = findNearestPlayerPoint(playersPositions, availableMoves);
            if (availablePos != null) {
                adversary.setPos(availablePos);
                return;
            }
        }

        for (Point p : availableMoves) {
            Tile tile = level.getTile(p);
            if (tile instanceof Door || tile instanceof Wall) {
                if (adversary.getType() == "zombie") {
                    // zombie can not leave the room it is spawned in
                    continue;
                }

                if (adversary.getType() == "ghost") {
                    List<Adversary> list = new ArrayList<Adversary>();
                    list.add(adversary);
                    level.randomAdversariesPlacement(list);
                }
            }
        }

    }

    private Point findNearestPlayerPoint(List<Point> playersPositions, List<Point> availablePositions) {
        HashMap<Point, Double> distanceByPositions = new HashMap<Point, Double>();

        for (Point p : availablePositions) {
            for (Point pl : playersPositions) {
                double distance = calculateDistanceBetweenPoints(p, pl);
                distanceByPositions.put(p, distance);
            }
        }

        Point x = null;
        double lowestValue = 2000;
        for (Map.Entry<Point, Double> en : distanceByPositions.entrySet()) {
            if (en.getValue() < lowestValue) {
                lowestValue = en.getValue();
                x = en.getKey();
            }
        }
        return x;
    }

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

    public Boolean getExitLocked() {
        return this.levels.get(0).getExitLocked();
    }

    public Level getCurrentLevel() {
        return this.levels.get(currentLevel);
    }

    public boolean nextLevel() {
        currentLevel++;
        if (currentLevel >= levels.size()) {
            return false;
        }

        // New Adversaries
        adversaries = new ArrayList<Adversary>();
        int zombieCount = Math.floorDiv(currentLevel, 2) + 1;
        for (int i = 0; i < zombieCount; i++) {
            Adversary zombie = new Zombie("Zombie" + currentLevel + i);
            adversaries.add(zombie);
        }
        int ghostCount = Math.floorDiv(currentLevel - 1, 2);
        for (int i = 0; i < ghostCount; i++) {
            Adversary ghost = new Ghost("Ghost" + currentLevel + i);
            adversaries.add(ghost);
        }
        adversaries = levels.get(currentLevel).randomAdversariesPlacement(adversaries);

        // Players
        for (Player player : this.players) {
            player.setIsExpelled(false);
        }

        players = levels.get(currentLevel).randomPlayersPlacement(players);

        return true;

    }

}
