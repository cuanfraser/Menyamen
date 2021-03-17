package org.menyamen.snarl.characters;

import java.awt.Point;

public class Adversary {

    private Point pos;
    
    //Adversary instructions to be provided 
    public Adversary(){}

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