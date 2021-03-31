package org.menyamen.snarl.manage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.awt.Point;

import org.json.JSONArray;
import org.json.JSONObject;
import org.menyamen.snarl.characters.Adversary;
import org.menyamen.snarl.characters.Player;
import org.menyamen.snarl.constraints.Move;
import org.menyamen.snarl.layout.Level;
import org.menyamen.snarl.state.FullState;

import static org.menyamen.snarl.util.TestingUtil.convertNameList;
import static org.menyamen.snarl.util.TestingUtil.convertPointList;
import static org.menyamen.snarl.util.TestingUtil.convertActorMoveListList;
import static org.menyamen.snarl.util.TestingUtil.jsonToLevel;
import static org.menyamen.snarl.util.TestingUtil.fullStateToJSON;
import static org.menyamen.snarl.util.TestingUtil.traceListToJSON;

public class TestManager {

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

        // (name-list) JSONArray
        JSONArray nameListJSON = inputJSONArray.getJSONArray(0);
        // (level) JSONObject
        JSONObject levelJSON = inputJSONArray.getJSONObject(1);
        // (natural) Max Turns
        int maxTurns = inputJSONArray.getInt(2);
        // (point-list) JSONArray of Player and Adversary Positions
        JSONArray positionsJSON = inputJSONArray.getJSONArray(3);
        // (actor-move-list-list) JSONArray
        JSONArray actorMoveListsJSON = inputJSONArray.getJSONArray(4);



        // convert (name-list) to List<Player>
        List<Player> playersList = convertNameList(nameListJSON);
        // convert (level) to Level
        Level level = jsonToLevel(levelJSON);
        // convert (point-list) to List<Point>
        List<Point> initialPositionsList = convertPointList(positionsJSON);
        // convert actor move list list
        List<List<Move>> movesList = convertActorMoveListList(actorMoveListsJSON);

        //Add initial positions to Players and Create Adversaries
        List<Adversary> adversariesList = new ArrayList<Adversary>();
        for (int i = 0; i < initialPositionsList.size(); i++) {
            Point current = initialPositionsList.get(i);
            if (i < playersList.size()) {
                playersList.get(i).setPos(current);
            }
            else {
                adversariesList.add(new Adversary(current, "zombie"));
            }
        }


        // Create GameManager
        GameManager gameManager = new GameManager(level, maxTurns, movesList);
        // Register Players
        for (Player currentPlayer : playersList) {
            gameManager.registerPlayer(currentPlayer);
        }
        // Register Adversaries
        for (Adversary currentAdversary : adversariesList) {
            gameManager.registerAdversary(currentAdversary);
        }

        gameManager.updatePlayers();

        gameManager.startGame();

        

        FullState finalState = gameManager.getFullState();
        JSONObject stateOutput = fullStateToJSON(finalState, levelJSON);
        // (manager-trace) JSON
        JSONArray managerTrace = traceListToJSON(gameManager.getTraceList());

        JSONArray output = new JSONArray();
        output.put(0, stateOutput);
        output.put(1, managerTrace);
        System.out.println(output);        

    }
    
}