package org.menyamen.snarl.characters;

import java.awt.Point;
import java.util.Scanner;

import org.menyamen.snarl.state.PlayerState;

public interface Player {

    /**
     * Gets the username of Player as String.
     * @return String for Player username.
     */
     String getName();

     /**
     * Gets the (X,Y) position of Player as @see Point.
     * @return Point for position of Player.
     */
     Point getPos();

     /**
     * Sets the (X,Y) position of Player as @see Point.
     * @return Point for new position of Player.
     */
    void setPos(Point pos);

     /**
     * Returns if this Player exists or not.
     * @return boolean if Player equals other Object.
     */
    // https://www.geeksforgeeks.org/overriding-equals-method-in-java/
     boolean equals(Object o);

    /**
     * Get Char representing the Player for ASCII Representation.
     * 
     * @return Char to represent the Player.
     */
    public char toChar();

    /**
     * Receive an update containing the player avatar’s position in the level and the current state of
     * their immediate surroundings. 
     * @param pos current position of player
     * @param state current state of immediate surroundings
     */
    public String update(Point pos, PlayerState state);

    public Point userMove(Scanner scanner, Player user);
}