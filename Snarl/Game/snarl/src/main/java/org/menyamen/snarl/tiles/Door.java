package org.menyamen.snarl.tiles;

import java.awt.Point;

import org.menyamen.snarl.characters.Adversary;
import org.menyamen.snarl.characters.Player;
import org.menyamen.snarl.gameobjects.GameObject;

public class Door implements Tile {
    private Point pos;
    private Player player;
    private Adversary adversary;

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
        return this.player;
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public Adversary getAdversary() {
        return this.adversary;
    }

    @Override
    public void setAdversary(Adversary adversary) {
        this.adversary = adversary;
    }

    @Override
    public boolean isValidForPlacement() {
        return false;
    }
    
}