package org.menyamen.snarl.state;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.awt.Point;

import org.json.JSONArray;
import org.json.JSONObject;
import org.menyamen.snarl.characters.Adversary;
import org.menyamen.snarl.characters.Player;
import org.menyamen.snarl.layout.Level;

import static org.menyamen.snarl.util.Util.fromRowCol;
import static org.menyamen.snarl.util.Util.toRowCol;
import static org.menyamen.snarl.layout.TestLevel.jsonToLevel;

public class TestState {

    /**
     * Program entry point
     * 
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

        // break up (state)
        JSONObject levelJSON = stateJSON.getJSONObject("level");
        JSONArray playerJSONArray = stateJSON.getJSONArray("players");
        JSONArray adversaryJSONArray = stateJSON.getJSONArray("adversaries");
        Boolean exitLocked = stateJSON.getBoolean("exit-locked");

        // (state) Conversions
        List<Player> playerList = convertPlayerList(playerJSONArray);
        List<Adversary> adversaryList = convertAdversariesList(adversaryJSONArray);
        Level level = jsonToLevel(levelJSON);
        FullState state = new FullState(level, playerList, adversaryList);

    }

    /**
     * Converts players (actor-position-list) from (state) to List<Player>.
     * 
     * @param playerJSONArray (actor-position-list) from (state).
     * @return List<Player> of Players from playerJSONArray.
     * @throws IllegalArgumentException If type of actor is not player.
     */
    private static List<Player> convertPlayerList(JSONArray playerJSONArray) throws IllegalArgumentException {
        List<Player> playerList = new ArrayList<Player>();

        for (int i = 0; i < playerJSONArray.length(); i++) {
            JSONObject currentPlayer = playerJSONArray.getJSONObject(i);
            if (!currentPlayer.getString("type").equals("player")) {
                throw new IllegalArgumentException(
                        "Expected Player in players list, found: " + currentPlayer.getString("type"));
            }
            Point point = fromRowCol(currentPlayer.getJSONArray("position"));
            Player player = new Player(currentPlayer.getString("name"), point);
            playerList.add(player);
        }

        return playerList;
    }

    /**
     * Converts adversaries (actor-position-list) from (state) to List<Adversary>.
     * 
     * @param adversaryJSONArray (actor-position-list) from (state).
     * @return List<Adversary> of Adversary from adversaryJSONArray.
     * @throws IllegalArgumentException If type of actor is not zombie or ghost.
     */
    private static List<Adversary> convertAdversariesList(JSONArray adversaryJSONArray)
            throws IllegalArgumentException {
        List<Adversary> adversaryList = new ArrayList<Adversary>();

        for (int i = 0; i < adversaryJSONArray.length(); i++) {
            JSONObject currentPlayer = adversaryJSONArray.getJSONObject(i);
            // if (currentPlayer.getString("type").equals("zombie")) {

            // } else {
            //     throw new IllegalArgumentException(
            //             "Expected Player in players list, found: " + currentPlayer.getString("type"));
            // }
            Point point = fromRowCol(currentPlayer.getJSONArray("position"));
            Adversary adversary = new Adversary(point);
            adversaryList.add(adversary);
        }

        return adversaryList;
    }

}