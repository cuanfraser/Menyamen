package org.menyamen.snarl.manage;

import java.util.List;
import java.awt.Point;

import org.menyamen.snarl.characters.Adversary;
import org.menyamen.snarl.characters.Player;
import org.menyamen.snarl.constraints.MoveResult;
import org.menyamen.snarl.layout.Level;
import org.menyamen.snarl.state.FullState;

/**
 * Specifies The class for the Game Manager which: - validates and accepts
 * players with unique names into the game - and starts a game with a single
 * level, which will be provided.
 */
public class GameManager {
    private FullState state;

    public GameManager(FullState state) {
        this.state = state;
    }

    public GameManager(Level level) {
        this.state = new FullState(level);
    }

    /**
     * Void function that starts the game with a single level
     *
     * @throws IllegalArgumentException if the level is not possible or a Player has
     *                                  an invalid name
     */
    void startGame() throws IllegalArgumentException {

        Boolean gameOver = false;

        while (state.getPlayers().size() > 0 && !gameOver) {
            for (Player player : state.getPlayers()) {
                //request move from player
                // Point point = client.getMove() etc.
                Point point = new Point();
                MoveResult result = state.move(player.getName(), point);
                // client.send(result);

                // As it is only level for now, exit means game over
                if (result == MoveResult.EXIT) {
                    System.out.println("Game ended");
                    gameOver = true;
                    break;
                }
            }
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

}