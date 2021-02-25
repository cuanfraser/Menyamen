package org.menyamen.snarl.layout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.menyamen.snarl.objects.GameObject;
import org.menyamen.snarl.tiles.Door;
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
     * Create Rectangular Room of given size at given origin Point using given layout.
     * @param origin Point for (x,y) coordinates of upper-left position.
     * @param horizontalSize Horizontal Size.
     * @param verticalSize Vertical Size.
     * @param layout 2D Array of ints where 0: Wall, 1: OpenTile, 2: Door.
     */
    public Room(Point origin, int horizontalSize, int verticalSize, int[][] layout) {
        this.origin = origin;
        this.horizontalSize = horizontalSize;
        this.verticalSize = verticalSize;
        this.useLayout(layout);
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

        // Potential Random Generator for Objects
        //List<Point> objectPoints = new ArrayList<Point>();
        // Random random = new Random();

        // // Generate Point list for Objects
        // for (GameObject currentObject : objects) {
            
        //     int x = origin.x + random.nextInt(horizontalSize - 2);
        //     int y = origin.y - random.nextInt(verticalSize - 2);
        //     Point randomPoint = new Point(x, y);
        //     objectPoints.add(randomPoint);
        // }

        for (int i = 0; i < verticalSize; i++) {
            for (int j = 0; j < horizontalSize; j++) {
                Point pos = new Point(origin);
                pos.translate(j, i);
                // Add Walls on edges
                if (i == 0 || i == (verticalSize - 1) || j == 0 || j == (horizontalSize - 1)) {
                    tiles.add(new Wall(pos));
                }
                // Add OpenTiles elsewhere
                else {
                    tiles.add(new OpenTile(pos));
                }
            }
        }
        return tiles;
    }

    /**
     * Use Layout for Room from 2D Array of Ints from Milestone 3
     * @param layout 2D Array of ints for layout.
     * @return List of Tiles needed for Room.
     * @throws IllegalArgumentException Invalid int value.
     */
    protected List<Tile> useLayout(int[][] layout) throws IllegalArgumentException {
        this.tiles = new ArrayList<Tile>();

        for(int i = 0; i < layout.length; i++) {
            for (int k = 0; k < layout[i].length; k++) {
                Tile newTile;
                Point currentPoint = new Point(origin);
                currentPoint.translate(k, i);
                if (layout[i][k] == 0) {
                    newTile = new Wall(currentPoint);
                }
                else if (layout[i][k] == 1) {
                    newTile = new OpenTile(currentPoint);
                }
                else if (layout[i][k] == 2) {
                    newTile = new Door(currentPoint);
                }
                else {
                    throw new IllegalArgumentException("Invalid layout");
                }
                this.tiles.add(newTile);
            }
        }
        return this.tiles;
    }

    /**
     * Checks if specified Point is inside Room by checking tiles.
     * @param point Specified Point to check for.
     * @return True if in tiles, false otherwise.
     */
    public Boolean inRoom(Point point) {
        for (Tile curTile : tiles) {
            if (curTile.getPos().equals(point)) {
                return true;
            }
        }
        return false;
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

    protected List<Tile> getTiles() {
        return this.tiles;
    }


}