package org.menyamen.snarl.trace;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.menyamen.snarl.characters.Adversary;
import org.menyamen.snarl.characters.Player;
import org.menyamen.snarl.gameobjects.GameObject;
import org.menyamen.snarl.state.PlayerState;
import org.menyamen.snarl.tiles.Tile;
import static org.menyamen.snarl.util.TestingUtil.toRowCol;
import static org.menyamen.snarl.util.TestingUtil.playerListToJSON;
import static org.menyamen.snarl.util.TestingUtil.adversaryListToJSON;
import static org.menyamen.snarl.util.TestingUtil.objectListToJSON;

public class PlayerUpdateTrace implements TraceEntry {

    private PlayerState state;

    public PlayerUpdateTrace(PlayerState state) {
        this.state = state;
    }

    @Override
    public JSONArray toJSON() {
        JSONArray output = new JSONArray();

        output.put(0, state.getPlayer().getName());
        output.put(1, playerStateToJSON());
        
        return output;
    }

    private JSONObject playerStateToJSON() {

        List<Player> players = state.getPlayers();
        List<Adversary> advers = state.getAdversaries();
        List<Tile> tiles = state.getTiles();
        
        List<GameObject> objects = new ArrayList<GameObject>();

        for (Tile tile : tiles) {
            if (tile.getGameObject() != null) {
                objects.add(tile.getGameObject());
            }
        }

        JSONArray playerPosList = playerListToJSON(players);
        for(int i = 0; i < playerPosList.length(); i++) {
            JSONObject playerObject = playerPosList.getJSONObject(i);
            if (playerObject.getString("name").equals(state.getPlayer().getName())) {
                playerPosList.remove(i);
                break;
            }
        }
        JSONArray adversPosList = adversaryListToJSON(advers);
        JSONArray actorPosList = new JSONArray(playerPosList);
        actorPosList.putAll(adversPosList);

        JSONObject output = new JSONObject();

        output.put("type", "player-update");
        // TODO: Tile convertor to JSON
        output.put("layout", state.print());
        output.put("position", toRowCol(state.getPlayer().getPos()));
        output.put("object", objectListToJSON(objects));
        output.put("actors", actorPosList);

        return output;
    }

}