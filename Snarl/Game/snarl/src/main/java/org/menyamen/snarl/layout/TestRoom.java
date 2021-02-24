package org.menyamen.snarl.layout;

import java.util.Scanner;
import java.awt.Point;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Class for TestRoom
 */
public final class TestRoom {
    private TestRoom() {
    }

    /**
     * Program entry point
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {

        // STDIN Input
        Scanner stdin = new Scanner(System.in);
        StringBuilder inputBuilder = new StringBuilder();
        while (stdin.hasNextLine()) {
            inputBuilder.append(stdin.nextLine());
        }
        stdin.close();

        JSONArray arrayInput = new JSONArray(inputBuilder.toString());

        JSONObject roomJSON = arrayInput.getJSONObject(0);
        int pointRow = arrayInput.getJSONArray(1).getInt(0);
        int pointCol = arrayInput.getJSONArray(1).getInt(1);
        Point testPoint = new Point(pointCol, pointRow);

        Point origin = new Point(roomJSON.getJSONArray("origin").getInt(0),
            roomJSON.getJSONArray("origin").getInt(1));
        int roomRows = roomJSON.getJSONObject("bounds").getInt("rows");
        int roomCols = roomJSON.getJSONObject("bounds").getInt("columns");

        JSONArray layoutJSON = roomJSON.getJSONArray("layout");
        int [][] layout = new int [roomRows][roomCols];
        for(int i = 0; i < roomRows; i++) {
            JSONArray currentRow = layoutJSON.getJSONArray(i);
            for (int k = 0; k < roomCols; k++) {
                layout[i][k] = currentRow.getInt(k);
            }
        }

        Room room = new Room(origin, roomCols, roomRows, layout);
        Level level = new Level(room, origin.x + roomCols + 5, origin.y + roomRows + 5);

        System.out.println(level.print());
    }
}
