package org.menyamen.snarl.manage;

import java.util.List;

import org.menyamen.snarl.characters.Player;
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

    /**
     * Void function that starts the game with a single level
     *
     * @param players List of valid players registered with unique names
     * @param level   The single level at which the game starts
     * @throws IllegalArgumentException if the level is not possible or a Player has
     *                                  an invalid name
     */
     void startGame () throws IllegalArgumentException {
    
    }

    /**
     * Registers a Player only after validating if the username is unique (only 1-4
     * players)
     *
     * @param player Player whose name is going to validated
     */
   public void register(Player player){}
 
    /**
    * Void function that acts like a switch case that updates the game as the Player makes moves.
    *
    * @return the updated game state
    * @throws IllegalArgumentException if the game state is invalid
    */
    public void update() throws IllegalArgumentException{}
 
 
 }