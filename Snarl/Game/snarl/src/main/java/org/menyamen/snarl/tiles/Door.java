package org.menyamen.snarl.tiles;

import java.awt.Point;

public class Door implements Tile {
    private Point pos;

    public Door(int x, int y) {
        this.pos = new Point(x, y);
    }

    public Door(Point pos) {
        this.pos = pos;
    }

    @Override
    public Point getPos() {
        return pos;
    }

    @Override
    public char toChar() {
        return '/';
    }
    
}