package org.menyamen.snarl.layout;

import java.util.HashMap;
import java.util.List;

import org.menyamen.snarl.tiles.Tile;

import java.awt.Point;

public class Level {
    private List<Room> rooms;
    private List<Hallway> hallways;
    private HashMap<Point, Tile> map;
    private int sizeX;
    private int sizeY;

    /**
     * Construct Level from Rooms, Hallways and Size of Level.
     * @param rooms Rooms to add to Level.
     * @param hallways Hallways to add to Level.
     * @param x Horizontal size of Level.
     * @param y Vertical size of Level.
     */
    public Level(List<Room> rooms, List<Hallway> hallways, int x, int y) {
        this.rooms = rooms;
        this.hallways = hallways;
        this.sizeX = x;
        this.sizeY = y;
        this.map = new HashMap<Point,Tile>();
        this.generate();
    }

    /**
     * Generates map for Level from List of Rooms and List of Hallways.
     */
    public void generate() {
        for (Room singleRoom : rooms) {
            if (validRoomPlacement(singleRoom)) {
                singleRoom.addToMap(map, sizeX, sizeY);
            }
            
        }
        for (Hallway singleHallway: hallways) {
            if (validHallwayPlacement(singleHallway)) {
                List<Tile> tiles = singleHallway.getTiles();
                for (Tile singleTile : tiles) {
                    map.put(singleTile.getPos(), singleTile);
                }
            }
        }
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
        for (Tile currentTile : tilesNeeded) {
            if (map.containsKey(currentTile.getPos())) {
                return false;
            }
            if (currentTile.getPos().x > sizeX || currentTile.getPos().y > sizeY) {
                return false;
            }
        }
        return true;
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
                    builder.append(curTile.toChar());
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