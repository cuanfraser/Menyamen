package org.menyamen.snarl.layout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.menyamen.snarl.objects.GameObject;
import org.menyamen.snarl.tiles.OpenTile;
import org.menyamen.snarl.tiles.Tile;
import org.menyamen.snarl.tiles.Wall;

import java.awt.Point;

public class Room {
    private Point origin;
    private int horizontalSize;
    private int verticalSize;
    private List<GameObject> objects;
    private List<Tile> tiles;

    /**
     * Create Empty Room of size 10x10 at given origin Point.
     * @param origin Point for (x,y) coordinates of upper-left position.
     */
    public Room(Point origin) {
        this.origin = origin;
        this.horizontalSize = 10;
        this.verticalSize = 10;
        this.generateTiles();
    }

    /**
     * Create Empty Rectangular Room of given size at given origin Point.
     * @param origin Point for (x,y) coordinates of upper-left position.
     * @param horizontalSize Horizontal Size.
     * @param verticalSize Vertical Size.
     */
    public Room(Point origin, int horizontalSize, int verticalSize) {
        this.origin = origin;
        this.horizontalSize = horizontalSize;
        this.verticalSize = verticalSize;
        this.generateTiles();
    }

    /**
     * Create Rectangular Room of given size at given origin Point with given objects.
     * @param origin Point for (x,y) coordinates of upper-left position.
     * @param horizontalSize Horizontal Size.
     * @param verticalSize Vertical Size.
     * @param objects Objects to include in Room (Must have valid Position Values).
     */
    public Room(Point origin, int horizontalSize, int verticalSize, List<GameObject> objects) {
        this(origin, horizontalSize, verticalSize);
        this.objects = objects;
        this.generateTiles();
    }

    /**
     * Generates the tiles needed for the Room.
     * @return Tiles generated.
     */
    protected List<Tile> generateTiles() {

        tiles = new ArrayList<Tile>();

        for (int i = 0; i < verticalSize; i++) {
            for (int j = 0; j < horizontalSize; j++) {
                Point pos = new Point(origin);
                pos.translate(j, i);
                if (i == 0 || i == (verticalSize - 1) || j == 0 || j == (horizontalSize - 1)) {
                    tiles.add(new Wall(j, i));
                }
                else {
                    tiles.add(new OpenTile(j, i));
                }
            }
        }
        return tiles;
    }

    /**
     * Alternative option to generating tiles and adding to Map in Level is giving the Room the 
     * Map itself.
     * @param map HashMap representing Level.
     * @param sizeX Horizontal Size of Level.
     * @param sizeY Vertical Size of Level.
     */
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

    protected Point getOrigin() {
        return this.origin;
    }

    protected int getHorizontalSize() {
        return this.horizontalSize;
    }

    protected int getVerticalSize() {
        return this.verticalSize;
    }


}