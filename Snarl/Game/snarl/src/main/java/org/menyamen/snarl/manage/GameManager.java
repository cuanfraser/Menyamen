package org.menyamen.snarl.manage;

import java.util.ArrayList;
import java.util.List;

import org.menyamen.snarl.characters.Adversary;
import org.menyamen.snarl.characters.Player;
import org.menyamen.snarl.constraints.Move;
import org.menyamen.snarl.constraints.MoveResult;
import org.menyamen.snarl.layout.Level;
import org.menyamen.snarl.state.FullState;
import org.menyamen.snarl.state.PlayerState;
import org.menyamen.snarl.trace.PlayerUpdateTrace;
import org.menyamen.snarl.trace.TraceEntry;

/**
 * Specifies The class for the Game Manager which: - validates and accepts
 * players with unique names into the game - and starts a game with a single
 * level, which will be provided.
 */
public class GameManager {
    private FullState state;
    private int turns = 100;
    private List<List<Move>> movesList;
    private List<TraceEntry> traceList = new ArrayList<TraceEntry>();

    public GameManager(FullState state) {
        this.state = state;
    }

    public GameManager(Level level) {
        this.state = new FullState(level);
    }

    public GameManager(Level level, int turns, List<List<Move>> movesList) {
        this.state = new FullState(level);
        this.turns = turns;
        this.movesList = movesList;
    }

    /**
     * Void function that starts the game with a single level
     *
     * @throws IllegalArgumentException if the level is not possible or a Player has
     *                                  an invalid name
     */
    public void startGame() throws IllegalArgumentException {

        Boolean gameOver = false;

        while (state.getPlayers().size() > 0 && !gameOver && turns > 0) {
            List<Player> players = state.getPlayers();
            for (int i = 0; i < players.size(); i++) {
                Player currentPlayer = players.get(i);
                //request move from player
                List<Move> playerMoves = movesList.get(i);
                // Attempt Move and repeat until valid Move.
                MoveResult result;
                do {
                    if (playerMoves.size() == 0) {
                        return;
                    }
                    result = state.move(currentPlayer.getName(), playerMoves.get(0));
                    playerMoves.remove(0);
                }
                while (result == MoveResult.INVALID || result == MoveResult.NOTTRAVERSABLE);

                updatePlayers();

                // As it is only level for now, exit means game over
                if (result == MoveResult.EXIT) {
                    System.out.println("Game ended");
                    gameOver = true;
                    break;
                }
            }
            turns--;
        }
    }

    /**
     * Registers a Player only after validating if the username is unique (only 1-4
     * players)
     *
     * @param player Player whose name is going to validated
     * @throws IllegalStateException when already 4 players or player already exists.
     */
    public void registerPlayer(Player player) throws IllegalStateException {
        List<Player> playersList = state.getPlayers();
        if (playersList.size() > 4) {
            throw new IllegalStateException("Maximum of 4 Players");
        }
        
        if (playersList.contains(player)) {
            throw new IllegalStateException("Player already exists in list.");
        }

        playersList.add(player);
        
    }

    /**
     * Registers an Adversary.
     *
     * @param adversary Adversary to add.
     */
    public void registerAdversary(Adversary adversary) {
        state.getAdversaries().add(adversary);
    }

    protected void updatePlayers() {
        for (Player player : state.getPlayers()) {
            PlayerState updateState = state.makePlayerState(player);
            player.update(player.getPos(), updateState);

            // Trace
            TraceEntry traceEntry = new PlayerUpdateTrace(updateState);
            traceList.add(traceEntry);
        }
    }

    protected FullState getFullState() {
        return this.state;
    }

    public List<TraceEntry> getTraceList() {
        return this.traceList;
    }

}