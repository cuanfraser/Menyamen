package org.menyamen.snarl.state;

import java.util.ArrayList;
import java.util.List;
import java.awt.Point;

import org.menyamen.snarl.characters.Adversary;
import org.menyamen.snarl.characters.Player;
import org.menyamen.snarl.constraints.MoveResult;
import org.menyamen.snarl.gameobjects.ExitPortal;
import org.menyamen.snarl.gameobjects.GameObject;
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
        this.currentLevel = currentLevel;
        this.levels = levels;
        this.players = players;
        this.adversaries = adversaries;
    }

    // Intermediate Game State for 1 Level
    public FullState(Level level, List<Player> players, List<Adversary> adversaries) {
        this.levels = new ArrayList<Level>();
        this.levels.add(level);
        this.players = players;
        this.adversaries = adversaries;
    }

    public MoveResult move(String name, Point point) {
        // Player not in state
        if (!players.contains(new Player(name)));
        // Not traversable point
        if (!levels.get(currentLevel).isTraversable(point)) {
            return MoveResult.NOTTRAVERSABLE;
        }
        // Adversaries
        for (Adversary currentAdv : adversaries) {
            if (currentAdv.getPos().equals(point)) {
                return MoveResult.EJECTED;
            }
        }

        GameObject object = levels.get(currentLevel).getObject(point);
        if (object instanceof ExitPortal) {
            if (!((ExitPortal)object).getLocked()) {
                return MoveResult.EXIT;
            }
            else {
                return MoveResult.SUCCESS;
            }
        }
        
        return MoveResult.SUCCESS;


    }




}