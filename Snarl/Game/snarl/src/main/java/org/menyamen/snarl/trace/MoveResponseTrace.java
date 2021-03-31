package org.menyamen.snarl.trace;

import org.json.JSONArray;
import org.menyamen.snarl.constraints.Move;
import org.menyamen.snarl.constraints.MoveResult;
import static org.menyamen.snarl.util.TestingUtil.moveToJSON;

public class MoveResponseTrace implements TraceEntry {
    private String playerName;
    private Move move;
    private MoveResult moveResult;

    public MoveResponseTrace(String playerName, Move move, MoveResult moveResult) {
        this.playerName = playerName;
        this.move = move;
        this.moveResult = moveResult;
    }

    @Override
    public JSONArray toJSON() {
        JSONArray output = new JSONArray();

        output.put(0, playerName);
        output.put(1, moveToJSON(move));
        output.put(2, resultToString());
        
        return output;
    }

    private String resultToString() {
        String output;
        switch(this.moveResult) {
            case SUCCESS:
                output = "OK";
                break;
            case EJECTED:
                output = "Eject";
                break;
            case KEY:
                output = "Key";
                break;
            case EXIT:
                output = "Exit";
                break;
            case INVALID:
                output = "Invalid";
                break;
            case NOTTRAVERSABLE:
                output = "Invalid";
                break;
            default:
                output = "ERROR";
                break;
        }
        return output;
    }
}