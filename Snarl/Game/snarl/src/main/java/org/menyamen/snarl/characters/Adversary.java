package org.menyamen.snarl.characters;

import java.awt.Point;

public class Adversary {

    private Point pos;
    private String type;
    private String name;

    // Adversary instructions to be provided
    public Adversary() {
    }

    public Adversary(Point pos, String type) {
        this.pos = pos;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Adversary(Point pos) {
        this.pos = pos;
    }

    public Point getPos() {
        return this.pos;
    }

    public void setPos(Point pos) {
        this.pos = pos;
    }

    /**
     * Get Char representing the Adversary for ASCII Representation.
     * 
     * @return Char to represent the Adversary.
     */
    public char toChar() {
        return 'A';
    }
}