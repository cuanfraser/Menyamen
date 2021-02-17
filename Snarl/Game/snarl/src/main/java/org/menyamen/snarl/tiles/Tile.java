package org.menyamen.snarl.tiles;

import java.awt.Point;

import org.menyamen.snarl.objects.GameObject;

/**
 * Interface for Tiles which represent any tile on a Level.
 */
public interface Tile {
    
    /**
     * Gets the (X,Y) position of Tile as @see Point.
     * @return Point for position of Tile.
     */
    Point getPos();

    /**
     * Get Char representing the Tile type for ASCII Representation.
     * @return Char to represent the Tile Class.
     */
    char toChar();

    /**
     * Get GameObject from Tile.
     * @return GameObject if found in Class, null otherwise.
     */
    public GameObject getGameObject();

    /**
     * Adds GameObject to Tile. Returns true if added, false if not valid for Class.
     * @param object Object to add.
     * @return True if added, false if not valid for Class.
     */
    Boolean setGameObject(GameObject object);
}