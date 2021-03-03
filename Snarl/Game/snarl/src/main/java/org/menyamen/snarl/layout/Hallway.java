package org.menyamen.snarl.layout;

import java.util.ArrayList;
import java.util.List;

import org.menyamen.snarl.tiles.Door;
import org.menyamen.snarl.tiles.OpenTile;
import org.menyamen.snarl.tiles.Tile;
import org.menyamen.snarl.tiles.Wall;

import java.awt.Point;

public class Hallway {
    private Point start;
    private Point end;
    private List<Point> waypoints;
    private List<Tile> tiles;

    /**
     * Create Hallway from given start Point to given end Point.
     * @param start Point to start Hallway.
     * @param end Point to end Hallway.
     */
    public Hallway(Point start, Point end) {
        this.start = start;
        this.end = end;
        this.waypoints = new ArrayList<Point>();
        this.generateTiles();
    }

    /**
     * Create Hallway from given Waypoints.
     * @param waypoints List of Waypoints to create Hallway from.
     */
    public Hallway(Point start, Point end, List<Point> waypoints) {
        this.start = start;
        this.end = end;
        this.waypoints = waypoints;
        this.generateTiles();
    }

    /**
     * Generates the tiles needed for the Hallway.
     * @return Tiles generated.
     * @throws IllegalArgumentException if a waypoint is not vertical/horizontal.
     */
    protected List<Tile> generateTiles() throws IllegalArgumentException {

        tiles = new ArrayList<Tile>();

        List<Point> points = this.waypoints;
        points.add(0, start);
        points.add(end);

        // Up (U), Down (D), Left (L), Right (R)
        char prevDir = ' ';

        // Loop Points
        for (int i = 0; i + 1 < points.size(); i++) {

            int x1 = points.get(i).x;
            int y1 = points.get(i).y;
            int x2 = points.get(i + 1).x;
            int y2 = points.get(i + 1).y;
            if (x1 == x2 && y1 == y2) {
                throw new IllegalArgumentException("Hallways can only be Horizontal or Vertical");
            }

            // Equal y cord, horiz hallway
            if (y1 == y2) {
                // Going right
                if (x2 - x1 >= 0) {
                    //Loop path
                    for (int j = x1; j <= x2; j++) {
                        // Start of Waypoint Special Case for Turns
                        if (j == x1 && i != 0) {
                            if (prevDir == 'D') {
                                // Add Bottom Left Wall
                                tiles.add(new Wall(j - 1, y1 + 1));
                                tiles.add(new OpenTile(j, y1));
                                tiles.add(new Wall(j, y1 + 1));
                            }
                            if (prevDir == 'U') {
                                // Add Top Left Wall
                                tiles.add(new Wall(j, y1 + 1));
                                tiles.add(new OpenTile(j, y1));
                                tiles.add(new Wall(j + 1, y1));
                            }

                            if (prevDir == 'L') {

                                throw new IllegalArgumentException("Can not go right to left");
                            }

                        }

                        // Going further right
                        else {
                            tiles.add(new Wall(j, y1 - 1));
                            tiles.add(new OpenTile(j, y1));
                            tiles.add(new Wall(j, y1 + 1));
                        }

                    }
                    prevDir = 'R';
                }

                // Going left
                else {
                    for (int j = x2; j <= x1; j++) {
                        // Start of Waypoint Special Case for Turns
                        if (j == x2 && i != 0) {
                            if (prevDir == 'D') {
                                // Add Top Left Wall
                                tiles.add(new Wall(j, y1 + 1));
                                tiles.add(new OpenTile(j, y1));
                                tiles.add(new Wall(j + 1, y1));
                            }
                            if (prevDir == 'U') {
                                // Add Bottom Left Wall
                                tiles.add(new Wall(j - 1, y1 + 1));
                                tiles.add(new OpenTile(j, y1));
                                tiles.add(new Wall(j, y1 + 1));
                            }
                            if (prevDir == 'R') {
                                throw new IllegalArgumentException("Can not go left to right");
                            }
                        }
                        // Going further left
                        else {
                            tiles.add(new Wall(j, y1 - 1));
                            tiles.add(new OpenTile(j, y1));
                            tiles.add(new Wall(j, y1 + 1));
                        }
                    }
                    prevDir = 'L';
                }
            }

            // Equal x cord, vertical hallway
            else if (x1 == x2) {
                // Going down
                if (y2 - y1 >= 0) {
                    for (int j = y1; j <= y2; j++) {
                        // Start of Waypoint Special Case for Turns
                        if (j == y1 && i != 0) {
                            if (prevDir == 'R') {
                                // Add Top Right Wall
                                tiles.add(new Wall(x1 + 1, j - 1));
                                tiles.add(new OpenTile(x1, j));
                                tiles.add(new Wall(x1, j + 1));
                            }
                            if (prevDir == 'L') {
                                // Add Bottom Right Wall
                                tiles.add(new Wall(x1 - 1, j + 1));
                                tiles.add(new OpenTile(x1, j));
                                tiles.add(new Wall(x1 - 1, j));
                            }
                            if (prevDir == 'U') {
                                throw new IllegalArgumentException("Can not go down to up");
                            }
                        }
                        //keep going down
                        else {
                            tiles.add(new Wall(x1 - 1, j));
                            tiles.add(new OpenTile(x1, j));
                            tiles.add(new Wall(x1 + 1, j));
                        }
                    }
                    prevDir = 'D';
                }

                // Going up
                else {
                    // Start of Waypoint Special Case for Turns
                    for (int j = y2; j <= y1; j++) {
                        if (j == y2 && i != 0) {
                            if (prevDir == 'R') {
                                tiles.add(new Wall(x1 - 1, j + 1));
                                tiles.add(new OpenTile(x1, j));
                                tiles.add(new Wall(x1 + 1, j + 1));
                            }
                            if (prevDir == 'L') {
                                tiles.add(new Wall(x1 + 1, j - 1));
                                tiles.add(new OpenTile(x1, j));
                                tiles.add(new Wall(x1 - 1, j - 1));
                            }
                            if (prevDir == 'D') {
                                throw new IllegalArgumentException("Can not go up to down");
                            }
                        }
                        //keep going up
                        else {
                            tiles.add(new Wall(x1 + 1, j));
                            tiles.add(new OpenTile(x1, j));
                            tiles.add(new Wall(x1 - 1, j - 1));
                        }
                        prevDir = 'U';
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

    protected Point getStart() {
        return this.start;
    }

    protected Point getEnd() {
        return this.end;
    }

}
