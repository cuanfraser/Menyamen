package org.menyamen.snarl.tiles;

import java.awt.Point;

import org.menyamen.snarl.characters.Adversary;
import org.menyamen.snarl.characters.Ghost;
import org.menyamen.snarl.characters.Player;
import org.menyamen.snarl.gameobjects.GameObject;

public class Wall implements Tile {
    private Point pos;
    private Ghost ghost;

    public Wall() {
    }

    public Wall(int x, int y) {
        this.pos = new Point(x, y);
    }

    public Wall(Point pos) {
        this.pos = pos;
    }

    @Override
    public char toChar() {
        return '+';
    }

    @Override
    public Point getPos() {
        return pos;
    }

    // Credit: https://www.geeksforgeeks.org/overriding-equals-method-in-java/
    @Override
    public boolean equals(Object o) {
        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /*
         * Check if o is an instance of Wall or not "null instanceof [type]" also
         * returns false
         */
        if (!(o instanceof Wall)) {
            return false;
        }

        // typecast o to Wall so that we can compare data members
        Wall c = (Wall) o;

        // Compare the data members and return accordingly
        return pos == c.pos;
    }

    @Override
    public Boolean setGameObject(GameObject object) {
        return false;
    }

    @Override
    public GameObject getGameObject() {
        return null;
    }

    @Override
    public Player getPlayer() {
        return null;
    }

    @Override
    public void setPlayer(Player player) throws IllegalArgumentException {
        throw new IllegalArgumentException("Can't set Player for Wall.");
    }

    @Override
    public Adversary getAdversary() {
        return null;
    }

    @Override
    public void setAdversary(Adversary adversary) throws IllegalArgumentException {
        this.ghost = (Ghost) adversary;
    }

    @Override
    public boolean isValidForPlacement() {
        return false;
    }
}