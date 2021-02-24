package org.menyamen.snarl.state;

import java.util.List;
import java.util.logging.Level;

import org.menyamen.snarl.characters.Adversary;
import org.menyamen.snarl.characters.Player;

public class FullState {
    Level level;
    List<Player> players;
    List<Adversary> adversaries;

    public FullState(Level level) {
        this.level = level;
    }
}