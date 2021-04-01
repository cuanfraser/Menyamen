package org.menyamen.snarl.characters;

import java.awt.Point;

public class Ghost extends Adversary {

    public Ghost(Point pos) {
        super(pos);
    }

    public Ghost(Point pos, String name) {
        super(pos, name);
    }

    @Override
    public String getType() {
        return "ghost";
    }

    @Override
    public char toChar() {
        return 'G';
    }
    
}