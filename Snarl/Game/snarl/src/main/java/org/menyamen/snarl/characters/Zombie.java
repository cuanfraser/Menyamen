package org.menyamen.snarl.characters;

import java.awt.Point;

public class Zombie implements Adversary {

    private Point pos;
    private String name;

    public Zombie(Point pos) {
        this.pos = pos;
    }

    public Zombie(Point pos, String name) {
        this.pos = pos;
        this.name = name;
    }

    @Override
    public Point getPos() {
        return this.pos;
    }

    @Override
    public void setPos(Point pos) {
        this.pos = pos;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
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