package org.menyamen.snarl.layout;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.awt.Point;

import org.json.JSONArray;
import org.json.JSONObject;
import org.menyamen.snarl.gameobjects.ExitPortal;
import org.menyamen.snarl.gameobjects.GameObject;
import org.menyamen.snarl.gameobjects.Key;

public class TestLevel {

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

        // (level) JSONObject input
        JSONObject levelJSON = arrayInput.getJSONObject(0);
        // (point) input
        Point testPoint = fromRowCol(arrayInput.getJSONArray(1));

        // break up (level)
        JSONArray roomList = levelJSON.getJSONArray("rooms");
        JSONArray hallwayList = levelJSON.getJSONArray("hallways");
        JSONArray objectList = levelJSON.getJSONArray("objects");

        // Convert JSON Lists
        List<Room> rooms = jsonToListRoom(roomList);
        List<Hallway> hallways = jsonToListHallways(hallwayList);


        // JSONObjects

        JSONObject keyJSON = objectList.getJSONObject(0);
        JSONObject exitJSON = objectList.getJSONObject(1);

        if (!keyJSON.getString("type").equals("key")) {
            throw new IllegalArgumentException("Wrong type of object. Expected key, got: " + 
                keyJSON.getString("type"));
        }
        
        if (!exitJSON.getString("type").equals("exit")) {
            throw new IllegalArgumentException("Wrong type of object. Expected exit, got: " + 
                keyJSON.getString("type"));
        }

        GameObject key = new Key();
        Point keyPoint = new Point(fromRowCol(keyJSON.getJSONArray("position")));
        GameObject exit = new ExitPortal();
        Point exitPoint = new Point(fromRowCol(exitJSON.getJSONArray("position")));

        Level level = new Level(rooms, hallways);
        level.addObject(key, keyPoint);
        level.addObject(exit, exitPoint);

        // Output
        Boolean traversable = level.isTraversable(testPoint);
        GameObject object = level.getObject(testPoint);
        String type = level.whereIsPoint(testPoint);
        List<Point> reachable = level.reachableFromPoint(testPoint);
        JSONArray reachableJSON = new JSONArray();
        for (Point curPoint : reachable) {
            reachableJSON.put(toRowCol(curPoint));
        }

        JSONObject output = new JSONObject();
        output.put("traversable", traversable);
        if (object == null) {
            output.put("object", JSONObject.NULL);
        }
        else {
            output.put("object", object.toString());
        }
        output.put("type", type);
        output.put("reachable", reachableJSON);

        System.out.println(output.toString());

        //System.out.println(level.print());

    }

    /**
     * Convert Point to (point) JSON
     * @param point
     * @return
     */
    public static JSONArray toRowCol(Point point) {
        JSONArray converted = new JSONArray();
        converted.put(point.y);
        converted.put(point.x);
        return converted;
    }

    /**
     * Convert (point) JSON to Point
     * @param rowCol
     * @return
     */
    public static Point fromRowCol(JSONArray rowCol) {
        int row = rowCol.getInt(0);
        int col = rowCol.getInt(1);
        Point converted = new Point(col, row);
        return converted;
    }

    /**
     * Convert JSONArray of room-list to List<Room>
     * @param roomList
     * @return
     * @throws IllegalArgumentException
     */
    public static List<Room> jsonToListRoom(JSONArray roomList) throws IllegalArgumentException {
        List<Room> outputList = new ArrayList<Room>();

        for (int i = 0; i < roomList.length(); i++) {
            JSONObject currentRoom = roomList.getJSONObject(i);

            // Check all items are Rooms
            String type = currentRoom.getString("type");
            if (!type.equals("room")) {
                throw new IllegalArgumentException("Wrong type of object in room list: " + type);
            } 

            Point origin = fromRowCol(currentRoom.getJSONArray("origin"));
            int roomRows = currentRoom.getJSONObject("bounds").getInt("rows");
            int roomCols = currentRoom.getJSONObject("bounds").getInt("columns");

            // Process layout Array from JSON into 2D int Array
            JSONArray layoutJSON = currentRoom.getJSONArray("layout");
            int[][] layout = new int[roomRows][roomCols];
            for (int k = 0; k < roomRows; k++) {
                JSONArray currentRow = layoutJSON.getJSONArray(k);
                for (int m = 0; m < roomCols; m++) {
                    layout[k][m] = currentRow.getInt(m);
                }
            }

            Room newRoom = new Room(origin, roomCols, roomRows, layout);
            outputList.add(newRoom);
        }

        return outputList;
    }

    /**
     * Convert JSONArray of hallway-list to List<Hallway>
     * @param hallwayList
     * @return
     */
    public static List<Hallway> jsonToListHallways(JSONArray hallwayList) {
        List<Hallway> outputList = new ArrayList<Hallway>();

        for (int i = 0; i < hallwayList.length(); i++) {
            JSONObject currentJSON = hallwayList.getJSONObject(i);

            // Check all items are Rooms
            String type = currentJSON.getString("type");
            if (!type.equals("hallway")) {
                throw new IllegalArgumentException("Wrong type of object in hallway list: " + type);
            } 

            Point from = fromRowCol(currentJSON.getJSONArray("from"));
            Point to = fromRowCol(currentJSON.getJSONArray("to"));

            JSONArray waypointsJSON = currentJSON.getJSONArray("waypoints");
            List<Point> waypoints = new ArrayList<Point>();

            for (int k = 0; k < waypointsJSON.length(); k++) {
                Point converted = fromRowCol(waypointsJSON.getJSONArray(k));
                waypoints.add(converted);
            }

            Hallway hallway = new Hallway(from, to, waypoints);
            outputList.add(hallway);
        }

        return outputList;
    }
}