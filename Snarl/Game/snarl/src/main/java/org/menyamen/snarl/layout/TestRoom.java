package org.menyamen.snarl.layout;

import java.util.List;
import java.util.Scanner;
import java.awt.Point;

import org.json.JSONArray;
import org.json.JSONObject;

import static org.menyamen.snarl.util.Util.fromRowCol;
import static org.menyamen.snarl.util.Util.toRowCol;

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

        // (room) JSONObject input
        JSONObject roomJSON = arrayInput.getJSONObject(0);
        // (point) input
        Point testPoint = fromRowCol(arrayInput.getJSONArray(1));

        // Break down (room)
        Point origin = fromRowCol(roomJSON.getJSONArray("origin"));
        int roomRows = roomJSON.getJSONObject("bounds").getInt("rows");
        int roomCols = roomJSON.getJSONObject("bounds").getInt("columns");

        // Process layout Array from JSON into 2D int Array
        JSONArray layoutJSON = roomJSON.getJSONArray("layout");
        int [][] layout = new int [roomRows][roomCols];
        for(int i = 0; i < roomRows; i++) {
            JSONArray currentRow = layoutJSON.getJSONArray(i);
            for (int k = 0; k < roomCols; k++) {
                layout[i][k] = currentRow.getInt(k);
            }
        }

        Room room = new Room(origin, roomCols, roomRows, layout);
        Level level = new Level(room);

        JSONArray outputArray = new JSONArray();

        if (room.inRoom(testPoint)) {
            List<Point> traversable = level.cardinalMove(testPoint, 1);
            JSONArray roomTraversable = new JSONArray();
            for (Point curPoint : traversable) {
                if (room.inRoom(curPoint)) {
                    roomTraversable.put(toRowCol(curPoint));
                }
            }

            outputArray.put(0, "Success: Traversable points from ");
            outputArray.put(1, arrayInput.getJSONArray(1));
            outputArray.put(2, " in room at ");
            outputArray.put(3, roomJSON.getJSONArray("origin"));
            outputArray.put(4, " are ");
            outputArray.put(5, roomTraversable);
        }
        else {
            outputArray.put(0, "Failure: Point ");
            outputArray.put(1, arrayInput.getJSONArray(1));
            outputArray.put(2, " is not in room at ");
            outputArray.put(3, roomJSON.getJSONArray("origin"));
        }

        System.out.println(outputArray.toString());
    }
}
