package org.menyamen.snarl.characters;

import java.awt.Point;

public class Adversary {

    private Point pos;
    
    public Adversary(){}

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