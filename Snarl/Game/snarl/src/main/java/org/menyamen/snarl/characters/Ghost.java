package org.menyamen.snarl.characters;

import java.awt.Point;

public class Ghost implements Adversary {

    private Point pos;
    private String name;

    public Ghost(Point pos) {
        this.pos = pos;
    }

    public Ghost(Point pos, String name) {
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
        return "ghost";
    }

    @Override
    public char toChar() {
        return 'G';
    }
    
}