package org.menyamen.snarl.state;

import java.util.ArrayList;
import java.util.List;
import java.awt.Point;

import org.menyamen.snarl.characters.Adversary;
import org.menyamen.snarl.characters.Player;
import org.menyamen.snarl.constraints.Move;
import org.menyamen.snarl.constraints.MoveResult;
import org.menyamen.snarl.gameobjects.GameObject;
import org.menyamen.snarl.gameobjects.GameObject.GameObjectType;
import org.menyamen.snarl.layout.Level;
import org.menyamen.snarl.tiles.Tile;

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
                players.remove(playerIndex);
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
                return MoveResult.KEY;
            }
        }

        return MoveResult.SUCCESS;

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

}