package org.menyamen.snarl.manage;

import java.util.List;

import org.menyamen.snarl.characters.Adversary;
import org.menyamen.snarl.characters.Player;
import org.menyamen.snarl.layout.Level;
import org.menyamen.snarl.state.FullState;

/**
 * Specifies The class for the Game Manager which: - validates and accepts
 * players with unique names into the game - and starts a game with a single
 * level, which will be provided.
 */
public class GameManager {
    private FullState state;

    public GameManager(FullState state) {
        this.state = state;
    }

    public GameManager(Level level) {
        this.state = new FullState(level);
    }

    /**
     * Void function that starts the game with a single level
     *
     * @throws IllegalArgumentException if the level is not possible or a Player has
     *                                  an invalid name
     */
    void startGame() throws IllegalArgumentException {
        
    }

    /**
     * Registers a Player only after validating if the username is unique (only 1-4
     * players)
     *
     * @param player Player whose name is going to validated
     * @throws IllegalStateException when already 4 players or player already exists.
     */
    public void registerPlayer(Player player) throws IllegalStateException {
        List<Player> playersList = state.getPlayers();
        if (playersList.size() > 4) {
            throw new IllegalStateException("Maximum of 4 Players");
        }
        
        if (playersList.contains(player)) {
            throw new IllegalStateException("Player already exists in list.");
        }

        playersList.add(player);
        
    }

    /**
     * Registers an Adversary.
     *
     * @param adversary Adversary to add.
     */
    public void registerAdversary(Adversary adversary) {
        state.getAdversaries().add(adversary);
    }

    /**
     * Void function that acts like a switch case that updates the game as the
     * Player makes moves.
     *
     * @return the updated game state
     * @throws IllegalArgumentException if the game state is invalid
     */
    public void update() throws IllegalArgumentException {
    }

}