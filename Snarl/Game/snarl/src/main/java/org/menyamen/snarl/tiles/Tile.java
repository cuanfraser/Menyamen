package org.menyamen.snarl.tiles;

import java.awt.Point;

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
}