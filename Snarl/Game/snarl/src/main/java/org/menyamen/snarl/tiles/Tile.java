package org.menyamen.snarl.tiles;

import java.awt.Point;

public interface Tile {
    
    Point getPos();

    char toChar();
}