package org.menyamen.snarl.manage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.awt.Point;

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
    private int turns = 99999;
    private List<List<Move>> movesList;
    private List<TraceEntry> traceList = new ArrayList<TraceEntry>();

    public GameManager(FullState state) {
        this.state = state;
    }

    public GameManager(Level level) {
        this.state = new FullState(level);
    }

    public GameManager(int currentLevel, List<Level> levels) {
        this.state = new FullState(currentLevel, levels);
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
            for (int i = 0; i < players.size() && !gameOver; i++) {
                Player currentPlayer = players.get(i);
                if (currentPlayer.getIsExpelled()) {
                    continue;
                }
                //request move from player
                Move currentMove = getLocalMove(currentPlayer);
                MoveResult result = state.move(currentPlayer.getName(), currentMove);
                // TODO:
                switch (result) {
                    case SUCCESS:
                        break;
                    case EJECTED:
                        currentPlayer.setIsExpelled(true);
                        break;
                    case KEY:
                    case EXIT: {
                        System.out.println("Player " + currentPlayer.getName() + " exited.");
                        if (state.nextLevel()) {

                        }
                        else {
                            System.out.println("Game has been won!");
                            gameOver = true;
                        }
                    }
                    case INVALID:
                    case NOTTRAVERSABLE:


                }

                // List<Move> playerMoves = movesList.get(i);
                // // Attempt Move and repeat until valid Move.
                // MoveResult result;
                // do {
                //     if (playerMoves.size() == 0 || playerMoves == null) {
                //         return;
                //     }
                //     result = state.move(currentPlayer.getName(), playerMoves.get(0));
                //     playerMoves.remove(0);
                // }
                // while (result == MoveResult.INVALID || result == MoveResult.NOTTRAVERSABLE);

                updatePlayers();

            }
            turns--;
        }
                while(state.getAdversaries().size() > 0 && !gameOver){
            List<Adversary> adversaries = state.getAdversaries();
            for (int i = 0; i < adversaries.size(); i++) {
                Adversary currentAdversary = adversaries.get(i);
                state.moveAdversary(currentAdversary);
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

    public Move getLocalMove(Player player) throws IllegalArgumentException {
        String moveInput;

        Scanner input = new Scanner(System.in);
        System.out.println("Would you like to move 1 or 2 cardinal moves?");
        int moveDistance = Integer.parseInt(input.nextLine());
        if (moveDistance == 0) {
            moveInput = "";
        }
        else if (moveDistance == 1) {
            System.out.println("Move commands:");
            System.out.println("'a': left");
            System.out.println("'d': right");
            System.out.println("'w': up");
            System.out.println("'s': down");
            System.out.println("Enter move:");
            moveInput = input.nextLine();
        }
        else if (moveDistance == 2) {
            System.out.println("Move commands:");
            System.out.println("'a': left 2");
            System.out.println("'d': right 2");
            System.out.println("'w': up 2");
            System.out.println("'s': down 2");
            System.out.println("'q': up left");
            System.out.println("'e': up right");
            System.out.println("'z': down left'");
            System.out.println("'c': down right");
            System.out.println("Enter move:");
            moveInput = input.nextLine();
        }
        else {
            input.close();
            throw new IllegalArgumentException("Invalid move distance");
        }
        input.close();

        Move move = new Move(null);
        Point playerPos = player.getPos();

        if (moveDistance == 0) {
            move = new Move(null);
        }
        else if (moveDistance == 1) {
            if (moveInput.equals("a")) {
                playerPos.translate(-1, 0);
                move = new Move(playerPos);
            } else if (moveInput.equals("d")) {
                playerPos.translate(1, 0);
                move = new Move(playerPos);
            } else if (moveInput.equals("w")) {
                playerPos.translate(0, -1);
                move = new Move(playerPos);
            } else if (moveInput.equals("s")) {
                playerPos.translate(0, 1);
                move = new Move(playerPos);
            } 
        }
        else if (moveDistance == 2) {
            if (moveInput.equals("a")) {
                playerPos.translate(-2, 0);
                move = new Move(playerPos);
            } else if (moveInput.equals("d")) {
                playerPos.translate(2, 0);
                move = new Move(playerPos);
            } else if (moveInput.equals("w")) {
                playerPos.translate(0, -2);
                move = new Move(playerPos);
            } else if (moveInput.equals("s")) {
                playerPos.translate(0, 2);
                move = new Move(playerPos);
            } else if (moveInput.equals("q")) {
                playerPos.translate(-1, -1);
                move = new Move(playerPos);
            } else if (moveInput.equals("e")) {
                playerPos.translate(1, -1);
                move = new Move(playerPos);
            } else if (moveInput.equals("z")) {
                playerPos.translate(-1, 1);
                move = new Move(playerPos);
            } else if (moveInput.equals("c")) {
                playerPos.translate(1, 1);
                move = new Move(playerPos);
            } 
        }

        return move;
    }

}
