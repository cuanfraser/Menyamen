package org.menyamen.snarl.layout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.menyamen.snarl.objects.GameObject;
import org.menyamen.snarl.tiles.Door;
import org.menyamen.snarl.tiles.OpenTile;
import org.menyamen.snarl.tiles.Tile;
import org.menyamen.snarl.tiles.Wall;

import java.awt.Point;

public class Level {
    private List<Room> rooms;
    private List<Hallway> hallways;
    private HashMap<Point, Tile> map;
    private int sizeX;
    private int sizeY;

    /**
     * Construct Level from single Room and Size of Level.
     * @param rooms Rooms to add to Level.
     * @param hallways Hallways to add to Level.
     */
    public Level(Room room) {
        List<Room> temp = new ArrayList<Room>();
        temp.add(room);
        this.rooms = temp;
        this.hallways = new ArrayList<Hallway>();
        this.map = new HashMap<Point,Tile>();
        this.generate();
    }

    /**
     * Construct Level from Rooms, Hallways and Size of Level.
     * @param rooms Rooms to add to Level.
     * @param hallways Hallways to add to Level.
     */
    public Level(List<Room> rooms, List<Hallway> hallways) {
        this.rooms = rooms;
        this.hallways = hallways;
        this.map = new HashMap<Point,Tile>();
        this.generate();
    }

    /**
     * Generates map for Level from List of Rooms and List of Hallways.
     */
    public void generate() {
        int largestX = 0;
        int largestY = 0;

        // Add Rooms
        for (Room singleRoom : rooms) {
            if (validRoomPlacement(singleRoom)) {
                List<Tile> tiles = singleRoom.getTiles();
                for (Tile singleTile : tiles) {
                    Point currentPoint = singleTile.getPos();
                    largestX = currentPoint.x > largestX ? currentPoint.x : largestX;
                    largestY = currentPoint.y > largestY ? currentPoint.y : largestY;
                    map.put(currentPoint, singleTile);
                }
            }

        }

        // Add Hallways
        for (Hallway singleHallway: hallways) {
            if (!validHallwayPlacement(singleHallway)) {
                throw new IllegalStateException("Hallway illegal placement. Start: " + 
                    singleHallway.getStart().toString());
            }
            else {
                List<Tile> tiles = singleHallway.getTiles();
                for (Tile singleTile : tiles) {
                    Point currentPoint = singleTile.getPos();
                    largestX = currentPoint.x > largestX ? currentPoint.x : largestX;
                    largestY = currentPoint.y > largestY ? currentPoint.y : largestY;
                    map.put(currentPoint, singleTile);
                }
            }
        }

        sizeX = largestX + 1;
        sizeY = largestY + 1;
    }

    /**
     * Checks if Room has a valid placement in current Level.
     * @param room Room to test.
     * @return True if valid, false if invalid.
     */
    public Boolean validRoomPlacement(Room room) {
        int horizontalSize = room.getHorizontalSize();
        int verticalSize = room.getVerticalSize();
        Point origin = room.getOrigin();

        for (int i = origin.y; i < origin.y + verticalSize; i++) {
            for (int j = origin.x; j < origin.x + horizontalSize; j++) {
                Point currentPos = new Point(j, i);
                if (map.containsKey(currentPos)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if Hallway has a valid placement in current Level.
     * @param hallway Hallway to test.
     * @return True if valid, false if invalid.
     */
    public Boolean validHallwayPlacement(Hallway hallway) {
        List<Tile> tilesNeeded = hallway.getTiles();

        if (! (map.get(hallway.getStart()) instanceof Door)) {
            return false;
        }

        if (! (map.get(hallway.getEnd()) instanceof Door)) {
            return false;
        }


        for (Tile currentTile : tilesNeeded) {
            Point currentPos = currentTile.getPos();
            if (map.containsKey(currentPos)) {
                if (map.get(currentPos) instanceof Wall && currentTile instanceof Wall) {
                    continue;
                }
                if (currentPos.equals(hallway.getStart())) {
                    continue;
                }
                if (currentPos.equals(hallway.getEnd())) {
                    continue;
                }
                else {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Is given Point in map next to a Tile of the same Class as given tile (above, below, left
     * or right of given Point).
     * @param point Point to check around on map.
     * @param tile Tile of Class type to check for (e.g. Wall, OpenTile, Door).
     * @return True if specified type is found, False if not.
     */
    private Boolean nextTo(Point point, Tile tile) {
        Point above = new Point(point);
        above.translate(0, -1);
        Point below = new Point(point);
        below.translate(0, 1);
        Point left = new Point(point);
        left.translate(-1, 0);
        Point right = new Point(point);
        right.translate(1, 0);

        if (map.containsKey(above)) {
            Tile ta = map.get(above);
            if (ta.getClass() == tile.getClass()) {
                return true;
            }
        }
        if (map.containsKey(below)) {
            Tile tb = map.get(below);
            if (tb.getClass() == tile.getClass()) {
                return true;
            }
        }
        if (map.containsKey(left)) {
            Tile tl = map.get(left);
            if (tl.getClass() == tile.getClass()) {
                return true;
            }
        }
        if (map.containsKey(right)) {
            Tile tr = map.get(right);
            if (tr.getClass() == tile.getClass()) {
                return true;
            }
        }
        return false;

    }

    public List<Point> traversablePoints(Point point) {

        List<Point> output = new ArrayList<Point>();

        Point above = new Point(point);
        above.translate(0, -1);
        Point below = new Point(point);
        below.translate(0, 1);
        Point left = new Point(point);
        left.translate(-1, 0);
        Point right = new Point(point);
        right.translate(1, 0);
        Point twoAbove = new Point(point);
        twoAbove.translate(0, -2);
        Point twoBelow = new Point(point);
        twoBelow.translate(0, 2);
        Point twoLeft = new Point(point);
        twoLeft.translate(-2, 0);
        Point twoRight = new Point(point);
        twoRight.translate(2, 0);
        Point aboveRight = new Point(point);
        aboveRight.translate(1, -1);
        Point aboveLeft = new Point(point);
        aboveLeft.translate(-1, -1);
        Point belowRight = new Point(point);
        belowRight.translate(1, 1);
        Point belowLeft = new Point(point);
        belowLeft.translate(-1, 1);

        List<Point> testArray = new ArrayList<Point>(Arrays.asList(
            above, below, left, right, twoAbove, twoBelow, twoLeft, twoRight,
            aboveRight, aboveLeft, belowRight, belowLeft
        ));

        for (Point curPoint : testArray) {
            Tile curTile = map.get(curPoint);
            if (!(curTile instanceof Wall)) {
                output.add(curPoint);
            }
        }

        return output;
    }

    public Boolean addObject(GameObject object, Point position) {
        if (map.containsKey(position)) {
            if (map.get(position) instanceof OpenTile) {
                return map.get(position).setGameObject(object);
            }
        }
        return false;
    }

    /**
     * Utilty function to add tile to map.
     * @param tile Tile to add.
     */
    protected void addTile(Tile tile) {
        Point pos = tile.getPos();
        map.put(pos, tile);
    }

    /**
     * Print Level as ASCII String.
     * @return Level as ASCII String.
     */
    public String print() {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                Point pos = new Point(j, i);
                if (map.containsKey(pos)) {
                    Tile curTile = map.get(pos);
                    if (curTile.getPlayer() != null) {
                        builder.append(curTile.getPlayer().toChar());
                    }
                    else if(curTile.getAdversary() != null) {
                        builder.append(curTile.getAdversary().toChar());
                    }
                    else if (curTile.getGameObject() != null) {
                        builder.append(curTile.getGameObject().toChar());
                    }
                    else {
                        builder.append(curTile.toChar());
                    }
                }
                else {
                    builder.append(' ');
                }

                if (j == (sizeX - 1)) {
                    builder.append('\n');
                }
            }
        }
        return builder.toString();
    }
}
