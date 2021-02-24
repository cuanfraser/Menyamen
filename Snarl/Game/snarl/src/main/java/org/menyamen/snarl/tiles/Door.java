package org.menyamen.snarl.tiles;

import java.awt.Point;

import org.menyamen.snarl.characters.Adversary;
import org.menyamen.snarl.characters.Player;
import org.menyamen.snarl.objects.GameObject;

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

    @Override
    public GameObject getGameObject() {
        return null;
    }

    @Override
    public Boolean setGameObject(GameObject object) {
        return false;
    }

    @Override
    public Player getPlayer() {
        return null;
    }

    @Override
    public Boolean setPlayer(Player player) {
        return false;
    }

    @Override
    public Adversary getAdversary() {
        return null;
    }

    @Override
    public Boolean setAdversary(Adversary adversary) {
        return false;
    }
    
}