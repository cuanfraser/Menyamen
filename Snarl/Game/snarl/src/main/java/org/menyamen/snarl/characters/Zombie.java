package org.menyamen.snarl.characters;

import java.awt.Point;

public class Zombie extends Adversary {

    public Zombie(String name) {
        super(name);
    }

    public Zombie(Point pos) {
        super(pos);
    }

    public Zombie(Point pos, String name) {
        super(pos, name);
    }


    @Override
    public String getType() {
        return "zombie";
    }

    @Override
    public char toChar() {
        return 'Z';
    }
    
}