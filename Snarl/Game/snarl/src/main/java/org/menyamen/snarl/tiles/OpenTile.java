package org.menyamen.snarl.tiles;

import java.awt.Point;

import org.menyamen.snarl.characters.Adversary;
import org.menyamen.snarl.characters.Player;
import org.menyamen.snarl.gameobjects.GameObject;

public class OpenTile implements Tile {
    private Boolean isOccupied;
    private GameObject gameObject;
    private Player player;
    private Adversary adversary;
    private Point pos;

    public OpenTile(Point pos) {
        this.pos = pos;
        this.isOccupied = false;
    }


    public OpenTile(int x, int y) {
        this.pos = new Point(x, y);
        this.isOccupied = false;
    }

    public OpenTile(int x, int y, Boolean isOccupied, GameObject gameObject, Boolean isDoor) {
        this.pos = new Point(x, y);
        this.isOccupied = isOccupied;
        this.gameObject = gameObject;
    }

    @Override
    public GameObject getGameObject() {
        return gameObject;
    }

    @Override
    public Boolean setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
        return true;
    }

    public Boolean getIsOccupied() {
        return isOccupied;
    }

    public void setIsOccupied(Boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    @Override
    public char toChar() {
        return '.';
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

        /* Check if o is an instance of OpenTile or not
            "null instanceof [type]" also returns false */
        if (!(o instanceof OpenTile)) {
            return false;
        }

        // typecast o to OpenTile so that we can compare data members
        OpenTile c = (OpenTile) o;

        // Compare the data members and return accordingly
        return pos == c.pos ;
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    @Override
    public Boolean setPlayer(Player player) {
        this.player = player;
        return true;
    }

    @Override
    public Adversary getAdversary() {
        return this.adversary;
    }

    @Override
    public Boolean setAdversary(Adversary adversary) {
        this.adversary = adversary;
        return true;
    }
}
