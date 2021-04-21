package org.menyamen.snarl.manage;

import java.awt.Point;
import java.util.List;

import org.menyamen.snarl.characters.Player;
import org.menyamen.snarl.characters.RemoteAdversary;
import org.menyamen.snarl.constraints.CharacterEnum;
import org.menyamen.snarl.constraints.MoveResult;
import org.menyamen.snarl.gameobjects.GameObject;
import org.menyamen.snarl.gameobjects.GameObject.GameObjectType;
import org.menyamen.snarl.layout.Level;
import org.menyamen.snarl.state.FullState;

// TODO: MOVE THINGS HERE
public class RuleChecker {

    /**
     * A move is
     * valid only if the start and end points are valid. Specific implementations
     * may place additional constraints on the validity of a move (Player vs
     * Adversary). A player can move to any traversable tile up to 2 cardinal moves
     * away from themselves.
     *
     * @param state FullState.
     * @param start Point to start Hallway.
     * @param end   Point to end Hallway.
     * @return MoveResult for move.
     * @throws IllegalArgumentException if the move is not possible (a point is not
     *                                  traversable)
     */
    protected MoveResult moveCheck(FullState state, Player player,Point end) throws IllegalArgumentException {
        Level level = state.getCurrentLevel();
        List<Point> possibleMoves = level.cardinalMove(player.getPos(), 2);
        if (!possibleMoves.contains(end)) {
            return MoveResult.NOTTRAVERSABLE;
        }

        if (level.isOccupied(end)) {
            return MoveResult.NOTTRAVERSABLE;
        }

        GameObject object = level.getObject(end);
        if (object.getType() == GameObjectType.EXIT) {
            if (!level.getExitLocked()) {
                return MoveResult.EXIT;
            }
            else {
                return MoveResult.SUCCESS;
            }
        }
        else if (object.getType() == GameObjectType.KEY) {
            return MoveResult.KEY;
        }

        return MoveResult.SUCCESS;
        
    }

    //Move Check used by game manager 
    protected MoveResult moveCheckAdversary(FullState state, RemoteAdversary adversary, Point end) throws IllegalArgumentException {
        Level level = state.getCurrentLevel();
        List<Point> possibleMoves = level.cardinalMove(adversary.getPos(), 1);
        if (!possibleMoves.contains(end)) {
            return MoveResult.NOTTRAVERSABLE;
        }
        if (level.isOccupiedBy(end) == CharacterEnum.ADVERSARY) {
            return MoveResult.INVALID;
        }
        if (adversary.getType().equals("zombie") && !level.isTraversable(end)) {
            return MoveResult.NOTTRAVERSABLE;
        }
        
        return MoveResult.SUCCESS;
        
    }

    //Game over check 
    protected boolean gameOverCheck(FullState state, int turns) {
        if (turns == 0) {
            return true;
        }

        List<Player> players = state.getPlayers();
        for (Player currentPlayer : players) {
            if (!currentPlayer.getIsExpelled()) {
                return false;
            }
        }
        return true;
    }
}
