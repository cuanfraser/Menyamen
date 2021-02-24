package org.menyamen.snarl.state;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.menyamen.snarl.characters.Adversary;
import org.menyamen.snarl.characters.Player;

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




}