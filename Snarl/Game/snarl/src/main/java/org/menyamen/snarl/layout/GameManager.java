package org.menyamen.snarl.layout;

import java.util.List;

import org.menyamen.snarl.characters.Player;

/**
 * Specifies The class for the Game Manager which: - validates and accepts
 * players with unique names into the game - and starts a game with a single
 * level, which will be provided.
 */
public class GameManager {
    private Player player;
    private Level level;

    /**
     * Void function that starts the game with a single level
     *
     * @param players List of valid players registered with unique names
     * @param level   The single level at which the game starts
     * @throws IllegalArgumentException if the level is not possible or a Player has
     *                                  an invalid name
     */
     void startGame (List<Player> players, Level level) throws IllegalArgumentException {
    
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(final Level level) {
        this.level = level;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(final Player player) {
        this.player = player;
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