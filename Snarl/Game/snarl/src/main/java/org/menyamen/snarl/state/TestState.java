package org.menyamen.snarl.state;

import java.util.Scanner;
import java.awt.Point;

import org.json.JSONArray;
import org.json.JSONObject;

import static org.menyamen.snarl.util.Util.fromRowCol;
import static org.menyamen.snarl.util.Util.toRowCol;

public class TestState {

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

        JSONArray inputJSONArray = new JSONArray(inputBuilder.toString());

        // (state) JSONObject input
        JSONObject stateJSON = inputJSONArray.getJSONObject(0);
        // (name) String input
        String name = inputJSONArray.getString(1);
        // (point) input
        Point testPoint = fromRowCol(inputJSONArray.getJSONArray(2));

    }
    
}