package org.menyamen.snarl.state;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.awt.Point;

import org.json.JSONArray;
import org.json.JSONObject;
import org.menyamen.snarl.characters.Adversary;
import org.menyamen.snarl.characters.Zombie;
import org.menyamen.snarl.characters.Player;
import org.menyamen.snarl.characters.PlayerImpl;
import org.menyamen.snarl.constraints.Move;
import org.menyamen.snarl.constraints.MoveResult;
import org.menyamen.snarl.layout.Level;

import static org.menyamen.snarl.util.TestingUtil.fromRowCol;
import static org.menyamen.snarl.util.TestingUtil.toRowCol;
import static org.menyamen.snarl.util.TestingUtil.jsonToLevel;
import static org.menyamen.snarl.util.TestingUtil.playerListToJSON;

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
        FullState state = new FullState(level, playerList, adversaryList, exitLocked);

        Move move = new Move(testPoint);
        MoveResult result = state.move(name, move);

        stateJSON.put("players", playerListToJSON(state.getPlayers()));
        stateJSON.put("exit-locked", state.getExitLocked());

        /*
        { 
            "type": "state",
            "level": (level),
            "players": (actor-position-list),
            "adversaries": (actor-position-list),
            "exit-locked": (boolean)
        }
        */
        JSONArray output = new JSONArray();
        if (result == MoveResult.SUCCESS || result == MoveResult.KEY) {
            output.put(0, result.toString());
            output.put(1, stateJSON);
        }
        else if (result == MoveResult.EJECTED) {
            output.put(0, "Success");
            output.put(1, "Player ");
            output.put(2, name);
            output.put(3, " was ejected.");
            output.put(4, stateJSON);
        }
        else if (result == MoveResult.EXIT) {
            output.put(0, "Success");
            output.put(1, "Player ");
            output.put(2, name);
            output.put(3, " exited.");
            output.put(4, stateJSON);
        }
        else if (result == MoveResult.INVALID) {
            output.put(0, "Failure");
            output.put(1, "Player ");
            output.put(2, name);
            output.put(3, " is not a part of the game.");
        }
        else if (result == MoveResult.NOTTRAVERSABLE) {
            output.put(0, "Failure");
            output.put(1, "The destination position ");
            output.put(2, toRowCol(testPoint));
            output.put(3, " is invalid.");
        }

        System.out.println(output.toString());

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
            Player player = new PlayerImpl(currentPlayer.getString("name"), point);
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
            Adversary adversary = new Zombie(point);
            adversaryList.add(adversary);
        }

        return adversaryList;
    }



}