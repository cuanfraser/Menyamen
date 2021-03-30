package org.menyamen.snarl.layout;

import java.util.List;
import java.util.Scanner;
import java.awt.Point;

import org.json.JSONArray;
import org.json.JSONObject;
import org.menyamen.snarl.gameobjects.GameObject;
import static org.menyamen.snarl.util.TestingUtil.fromRowCol;
import static org.menyamen.snarl.util.TestingUtil.toRowCol;
import static org.menyamen.snarl.util.TestingUtil.jsonToLevel;

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

        Level level = jsonToLevel(levelJSON);

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
}