package org.menyamen.snarl.state;

import java.util.ArrayList;
import java.util.List;

import org.menyamen.snarl.characters.Adversary;
import org.menyamen.snarl.characters.Player;
import org.menyamen.snarl.layout.Level;

public class FullState {
    List<Level> levels;
    List<Player> players;
    List<Adversary> adversaries;

    public FullState(Level level) {
        this.levels = new ArrayList<Level>();
        this.levels.add(level);
    }

    // Intermediate Game State
    public FullState(List<Level> levels, List<Player> players, List<Adversary> adversaries) {
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




}