package org.menyamen.snarl.layout;

import java.util.HashMap;
import java.util.List;

import org.menyamen.snarl.tiles.Tile;

import java.awt.Point;

public class Level {
    private List<Room> rooms;
    private HashMap<Point, Tile> map;
    private int sizeX;
    private int sizeY;


    public Level(List<Room> rooms, int x, int y) {
        this.rooms = rooms;
        this.sizeX = x;
        this.sizeY = y;
        this.map = new HashMap<Point,Tile>();
        this.generate();
    }

    public void generate() {
        for (Room singleRoom : rooms) {
            singleRoom.addToMap(map, sizeX, sizeY);
        }
    }

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