package org.menyamen.snarl.characters;

import java.awt.Point;

public class Player {
    private String name;
    private Boolean isExpelled;
    private Point pos;

    public Player(String name) {
        this.name = name;
        this.isExpelled = false;
    }

    public Player(String name, Point pos) {
        this(name);
        this.pos = pos;
    }

    public Player(String name, int x, int y) {
        this(name);
        this.pos = new Point(x, y);
    }


    public String getName() {
        return name;
    }

    public Point getPos() {
        return this.pos;
    }

    public void setPos(Point pos) {
        this.pos = pos;
    }


    /**
     * Get Char representing the Player for ASCII Representation.
     * 
     * @return Char to represent the Player.
     */
    public char toChar() {
        return 'P';
    }
}