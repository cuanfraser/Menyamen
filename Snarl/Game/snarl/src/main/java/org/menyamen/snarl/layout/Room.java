package org.menyamen.snarl.layout;

import java.util.HashMap;

import org.menyamen.snarl.tiles.OpenTile;
import org.menyamen.snarl.tiles.Tile;
import org.menyamen.snarl.tiles.Wall;

import java.awt.Point;

public class Room {
    private Point origin;
    private int horizontalSize;
    private int verticalSize;

    public Room(Point origin) {
        this.origin = origin;
        this.horizontalSize = 10;
        this.verticalSize = 10;
    }

    public Room(Point origin, int horizontalSize, int verticalSize) {
        this.origin = origin;
        this.horizontalSize = horizontalSize;
        this.verticalSize = verticalSize;
    }

    public void addToMap(HashMap<Point, Tile> map, int sizeX, int sizeY) {
        for (int i = 0; i < verticalSize; i++) {
            for (int j = 0; j < horizontalSize; j++) {
                Point pos = new Point(origin);
                pos.translate(j, i);
                if (i == 0 || i == (verticalSize - 1) || j == 0 || j == (horizontalSize - 1)) {
                    map.put(pos, new Wall(j, i));
                }
                else {
                    map.put(pos, new OpenTile(j, i));
                }
            }
        }
    }

    
}