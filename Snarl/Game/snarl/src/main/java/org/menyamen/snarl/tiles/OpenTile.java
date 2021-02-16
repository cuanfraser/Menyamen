package org.menyamen.snarl.tiles;

import java.awt.Point;

import org.menyamen.snarl.objects.GameObject;

public class OpenTile implements Tile {
    private Boolean isOccupied;
    private GameObject gameObject;
    private Point pos;

    public OpenTile(int x, int y) {
        this.pos = new Point(x, y);
        this.isOccupied = false;
    }

    public OpenTile(int x, int y, Boolean isOccupied, GameObject gameObject, Boolean isDoor) {
        this.pos = new Point(x, y);
        this.isOccupied = isOccupied;
        this.gameObject = gameObject;
    }



    
    public GameObject getGameObject() {
        return gameObject;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
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
}