package org.menyamen.snarl.state;

import java.util.ArrayList;
import java.util.List;
import java.awt.Point;

import org.menyamen.snarl.characters.Adversary;
import org.menyamen.snarl.characters.Player;
import org.menyamen.snarl.constraints.Move;
import org.menyamen.snarl.constraints.MoveResult;
import org.menyamen.snarl.gameobjects.ExitPortal;
import org.menyamen.snarl.gameobjects.GameObject;
import org.menyamen.snarl.gameobjects.GameObject.GameObjectType;
import org.menyamen.snarl.layout.Level;


public class FullState {
    int currentLevel = 0;
    List<Level> levels;
    List<Player> players;
    List<Adversary> adversaries;

    public FullState(Level level) {
        this.levels = new ArrayList<Level>();
        this.levels.add(level);
    }

    // Intermediate Game State
    public FullState(int currentLevel, List<Level> levels, List<Player> players, List<Adversary> adversaries) {
        // TODO
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
     * @param name String name of Player to attempt to move.
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
        if (object.getType() == GameObjectType.EXIT) {
            if (!levels.get(currentLevel).getExitLocked()) {
                players.remove(playerIndex);
                return MoveResult.EXIT;
            }
            else {
                return MoveResult.SUCCESS;
            }
        }
        else if (object.getType() == GameObjectType.KEY) {
            levels.get(currentLevel).setExitLocked(false);
            return MoveResult.KEY;
        }
        
        return MoveResult.SUCCESS;


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