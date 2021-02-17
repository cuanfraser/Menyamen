package org.menyamen.snarl.layout;

import java.util.ArrayList;
import java.util.List;

import org.menyamen.snarl.tiles.Door;
import org.menyamen.snarl.tiles.OpenTile;
import org.menyamen.snarl.tiles.Tile;
import org.menyamen.snarl.tiles.Wall;

import java.awt.Point;

public class Hallway {
    private List<Point> waypoints;
    private List<Tile> tiles;
    private List<Tile> doors;

    /**
     * Create Hallway from given start Point to given end Point.
     * @param start Point to start Hallway.
     * @param end Point to end Hallway.
     */
    public Hallway(Point start, Point end) {
        this.waypoints = new ArrayList<Point>();
        this.waypoints.add(start);
        this.waypoints.add(end);
        this.generateTiles();
    }

    /**
     * Create Hallway from given Waypoints.
     * @param waypoints List of Waypoints to create Hallway from.
     */
    public Hallway(List<Point> waypoints) {
        this.waypoints = waypoints;
        this.generateTiles();
    }

    /**
     * Generates the tiles needed for the Hallway.
     * @return Tiles generated.
     * @throws IllegalArgumentException if a waypoint is not vertical/horizontal.
     */
    protected List<Tile> generateTiles() throws IllegalArgumentException{

        tiles = new ArrayList<Tile>();
        doors = new ArrayList<Tile>();

        for (int i = 0; i + 1 < waypoints.size(); i++) {

            int x1 = waypoints.get(i).x;
            int y1 = waypoints.get(i).y;
            int x2 = waypoints.get(i + 1).x;
            int y2 = waypoints.get(i + 1).y;

            if (x1 == x2 && y1 == y2) {
                throw new IllegalArgumentException("Hallways can only be Horizontal or Vertical");
            }

            // Equal y cord, horiz hallway
            if (y1 == y2) {
                // Going right
                if (x2 - x1 >= 0) {
                    // Add first door
                    if (i == 0) {
                        Tile door = new Door(x1 - 1, y1);
                        doors.add(door);
                    }
                    // Add last door
                    if (i + 1 == waypoints.size() - 1) {
                        Tile door = new Door(x2 + 1, y2);
                        doors.add(door);
                    }
                    for (int j = x1; j <= x2; j++) {
                        tiles.add(new Wall(j, y1 - 1));
                        tiles.add(new OpenTile(j, y1));
                        tiles.add(new Wall(j, y1 + 1));
                    }
                }
                // Going left
                else {
                    // Add first door
                    if (i == 0) {
                        Tile door = new Door(x1 + 1, y1);
                        doors.add(door);
                    }
                    // Add last door
                    if (i + 1 == waypoints.size() - 1) {
                        Tile door = new Door(x2 - 1, y2);
                        doors.add(door);
                    }
                    for (int j = x2; j <= x1; j++) {
                        tiles.add(new Wall(j, y1 - 1));
                        tiles.add(new OpenTile(j, y1));
                        tiles.add(new Wall(j, y1 + 1));
                    }
                }
            }
            // Equal x cord, vertical hallway
            else if (x1 == x2) {
                // Going down
                if (y2 - y1 >= 0) {
                    // Add first door
                    if (i == 0) {
                        Tile door = new Door(x1, y1 - 1);
                        doors.add(door);
                    }
                    // Add last door
                    if (i + 1 == waypoints.size() - 1) {
                        Tile door = new Door(x2, y2 + 1);
                        doors.add(door);
                    }
                    for (int j = y1; j <= y2; j++) {
                        tiles.add(new Wall(x1 - 1, j));
                        tiles.add(new OpenTile(x1, j));
                        tiles.add(new Wall(x1 + 1, j));
                    }
                }
                // Going up
                else {
                    // Add first door
                    if (i == 0) {
                        Tile door = new Door(x1, y1 + 1);
                        doors.add(door);
                    }
                    // Add last door
                    if (i + 1 == waypoints.size() - 1) {
                        Tile door = new Door(x2, y2 - 1);
                        doors.add(door);
                    }
                    for (int j = y2; j <= y1; j++) {
                        tiles.add(new Wall(x1 - 1, j));
                        tiles.add(new OpenTile(x1, j));
                        tiles.add(new Wall(x1 + 1, j));
                    }
                }
            }
        }

        return tiles;

    }

    protected List<Point> getWaypoints() {
        return this.waypoints;
    }

    protected List<Tile> getTiles() {
        return this.tiles;
    }

    protected List<Tile> getDoors() {
        return this.doors;
    }
    
}