package org.menyamen.snarl.util;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.menyamen.snarl.characters.Adversary;
import org.menyamen.snarl.characters.Player;
import org.menyamen.snarl.characters.PlayerImpl;
import org.menyamen.snarl.constraints.Move;
import org.menyamen.snarl.gameobjects.ExitPortal;
import org.menyamen.snarl.gameobjects.GameObject;
import org.menyamen.snarl.gameobjects.Key;
import org.menyamen.snarl.layout.Hallway;
import org.menyamen.snarl.layout.Level;
import org.menyamen.snarl.layout.Room;
import org.menyamen.snarl.state.FullState;
import org.menyamen.snarl.trace.TraceEntry;

public final class TestingUtil {

    private TestingUtil() {
    }

    /**
     * Convert Point to (point) JSON
     * 
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
     * Convert (point) JSON to Point.
     * 
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
     * Converts JSONArray of (actor-move-list-list) to List<List<Move>>.
     * @param jsonList (actor-move-list-list) JSONArray.
     * @return List<List<Move>>.
     */
    public static List<List<Move>> convertActorMoveListList(JSONArray jsonList) {

        List<List<Move>> output = new ArrayList<List<Move>>();
        
        for (int i = 0; i < jsonList.length(); i++) {
            List<Move> currentMoves = new ArrayList<Move>();
            JSONArray currentArray = jsonList.getJSONArray(i);
            for (int k = 0; k < currentArray.length(); k++) {
                
                JSONObject currentObject = currentArray.getJSONObject(k);
                if (!currentObject.getString("type").equals("move")) {
                    throw new IllegalArgumentException("Non 'move' type in (actor-move), type was: " + currentObject.getString("type"));
                }

                if (currentObject.optJSONArray("to") == null) {
                    currentMoves.add(new Move(null));
                }
                else {
                    Point point = fromRowCol(currentObject.getJSONArray("to"));
                    currentMoves.add(new Move(point));
                }
            }
            output.add(currentMoves);
        }
        
        return output;
    }

    /**
     * Convert JSONArray of (point-list) to List<Point>.
     * @param jsonList (point-list) JSONArray.
     * @return List<Point> from jsonList.
     */
    public static List<Point> convertPointList(JSONArray jsonList) {
        List<Point> output = new ArrayList<Point>();

        for (int i = 0; i < jsonList.length(); i++) {
            Point point = fromRowCol(jsonList.getJSONArray(i));
            output.add(point);
        }

        return output;

    }

    /**
     * Convert JSONArray of (name-list) to List<Player>.
     * @param nameList (name-list) JSONArray.
     * @return List<Player> from nameList.
     */
    public static List<Player> convertNameList(JSONArray nameList) {
        List<Player> output = new ArrayList<Player>();
        for (int i = 0; i < nameList.length(); i++) {
            Player currentPlayer = new PlayerImpl(nameList.getString(i));
            output.add(currentPlayer);
        }
        return output;
    }

    /**
     * Convert JSONArray of room-list to List<Room>
     * 
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
     * 
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

    /**
     * Convert (level) JSON to Level.
     * 
     * @param levelJSON Input (level) JSON.
     * @return Level.
     */
    public static Level jsonToLevel(JSONObject levelJSON) {
        // break up (level)
        JSONArray roomList = levelJSON.getJSONArray("rooms");
        JSONArray hallwayList = levelJSON.getJSONArray("hallways");
        JSONArray objectList = levelJSON.getJSONArray("objects");

        // Convert JSON Lists
        List<Room> rooms = jsonToListRoom(roomList);
        List<Hallway> hallways = jsonToListHallways(hallwayList);

        Level level = new Level(rooms, hallways);

        // JSONObjects
        for (int i = 0; i < objectList.length(); i++) {
            JSONObject currentJSON = objectList.getJSONObject(i);
            String type = currentJSON.getString("type");
            if (type.equals("key")) {
                GameObject key = new Key();
                Point keyPoint = new Point(fromRowCol(currentJSON.getJSONArray("position")));
                level.addObject(key, keyPoint);
            } else if (type.equals("exit")) {
                GameObject exit = new ExitPortal();
                Point exitPoint = new Point(fromRowCol(currentJSON.getJSONArray("position")));
                level.addObject(exit, exitPoint);
            } else {
                throw new IllegalArgumentException("Wrong type of object. Expected key/exit, got: " + type);
            }
        }

        return level;
    }

    // -------------------------------------------------------------------------
    // TO JSON:
    // -------------------------------------------------------------------------

    /**
     * Convert Move to (actor-move) JSON.
     * @param move Move to convert.
     * @return (actor-move) JSON.
     */
    public static JSONObject moveToJSON(Move move) {
        JSONObject output = new JSONObject();
        output.put("type", "move");
        if (move.getStayStill()) {
            output.put("to", JSONObject.NULL);
        }
        else {
            output.put("to", toRowCol(move.getDestination()));
        }
        return output;
    }

    public static JSONArray objectListToJSON(List<GameObject> objects) {
        JSONArray output = new JSONArray();

        for (GameObject currentObject : objects) {
            output.put(currentObject.toString());
        }

        return output;
    }

    public static JSONArray traceListToJSON(List<TraceEntry> traceList) {
        JSONArray output = new JSONArray();
        for (TraceEntry entry : traceList) {
            output.put(entry.toJSON());
        }
        return output;
    }

    /**
     * Convert List<Player> to (actor-position-list) JSON.
     * @param playerList List<Player> to convert.
     * @return (actor-position-list) JSON.
     */
    public static JSONArray playerListToJSON(List<Player> playerList) {
        JSONArray output = new JSONArray();
        for (Player player : playerList) {
            JSONObject currentObject = new JSONObject();
            currentObject.put("type", "player");
            currentObject.put("name", player.getName());
            currentObject.put("position", toRowCol(player.getPos()));
            output.put(currentObject);
        }
        return output;
    }

    /**
     * Convert List<Adversary> to (actor-position-list) JSON.
     * @param adversaryList List<Adversary> to convert.
     * @return (actor-position-list) JSON.
     */
    public static JSONArray adversaryListToJSON(List<Adversary> adversaryList) {
        JSONArray output = new JSONArray();
        for (Adversary adversary : adversaryList) {
            JSONObject currentObject = new JSONObject();
            currentObject.put("type", adversary.getType());
            currentObject.put("name", adversary.getName());
            currentObject.put("position", toRowCol(adversary.getPos()));
            output.put(currentObject);
        }
        return output;
    }

    // public static JSONArray hallwayListToJSON(List<Hallway> hallwayList) {
    //     JSONArray output = new JSONArray();

    //     JSONObject currentObject = new JSONObject();
    //     currentObject.put("type", "hallway");
    //     currentObject.put("from", "level");
    //     currentObject.put("to", "level");
    //     currentObject.put("waypoints", "level");
    //     return output;
    // }

    // public static JSONObject levelToJSON(Level level) {
    //     JSONObject output = new JSONObject();
    //     output.put("type", "level");
    //     output.put("rooms", "level");
    //     output.put("hallways", "level");
    //     output.put("objects", "level");
    //     return output;
    // }

    /**
     * Convert FullState to (state) JSON.
     * @param state FullState to convert.
     * @param levelJSON JSONObject (level) for Level.
     * @return (state) JSON.
     */
    public static JSONObject fullStateToJSON(FullState state, JSONObject levelJSON) {
        JSONObject output = new JSONObject();
        output.put("type", "state");
        output.put("level", levelJSON);
        output.put("players", playerListToJSON(state.getPlayers()));
        output.put("adversaries", adversaryListToJSON(state.getAdversaries()));
        output.put("exit-locked", state.getExitLocked());
        return output;
    }
}