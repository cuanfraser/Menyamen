package org.menyamen.snarl.tiles;

import java.awt.Point;

public class Wall implements Tile {
    private Point pos;

    public Wall(int x, int y) {
        this.pos = new Point(x, y);
    }

    @Override
    public char toChar() {
        return '+';
    }

    @Override
    public Point getPos() {
        return pos;
    }
}