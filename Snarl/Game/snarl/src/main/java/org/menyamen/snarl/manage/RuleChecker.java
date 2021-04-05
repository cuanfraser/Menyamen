package org.menyamen.snarl.manage;

import java.awt.Point;
import java.util.List;

import org.menyamen.snarl.characters.Adversary;
import org.menyamen.snarl.characters.Player;
import org.menyamen.snarl.constraints.CharacterEnum;
import org.menyamen.snarl.constraints.MoveResult;
import org.menyamen.snarl.gameobjects.GameObject;
import org.menyamen.snarl.gameobjects.GameObject.GameObjectType;
import org.menyamen.snarl.layout.Level;
import org.menyamen.snarl.state.FullState;
import org.menyamen.snarl.tiles.Door;
import org.menyamen.snarl.tiles.Tile;
import org.menyamen.snarl.tiles.Wall;

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

     /**
     * A move is
     * valid only if the start and end points are valid. Specific implementations
     * may place additional constraints on the validity of a move (Player vs
     * Adversary). A adversary can move to any traversable tile up to 1 cardinal move
     * away from themselves.
     *
     * @param state FullState.
     * @param start Point to start Hallway.
     * @param end   Point to end Hallway.
     * @return MoveResult for move.
     * @throws IllegalArgumentException if the move is not possible (a point is not
     *                                  traversable)
     */
    protected MoveResult moveCheckAdversary(FullState state, Adversary adversary, Point end) throws IllegalArgumentException {
        Level level = state.getCurrentLevel();
        //Possible cardinal moves, one move away from Adversary
        List<Point> possibleMoves = level.cardinalMove(adversary.getPos(), 1);
        //skip turn if end is not traversable 
        if (!possibleMoves.contains(end)) {
            return MoveResult.NOTTRAVERSABLE;
        }

        //An adversary can not move to a tile which is occupied by another adversary 
        if (level.isOccupiedBy(end) == CharacterEnum.ADVERSARY) {
            return MoveResult.NOTTRAVERSABLE;
        }

        //The player is getting ejected by the adversary 
        if (level.isOccupiedBy(end) == CharacterEnum.PLAYER) {
            return MoveResult.EJECTED;
        }

        //is adversary zombie or ghost 
        if (adversary.getType() == "zombie") {
            Tile tile = level.getTile(end);

            //zombie can not leave the room it is spawned in
            if (tile instanceof Door || tile instanceof Wall) {
                return MoveResult.NOTTRAVERSABLE;
            }   
        }
        if (adversary.getType() == "ghost") {
            Tile tile = level.getTile(end);

            //ghost can not leave the room it is spawned in
            if (tile instanceof Door || tile instanceof Wall) {
                return MoveResult.NOTTRAVERSABLE;
            }   
        }
       
        
         
        else if (object.getType() == GameObjectType.KEY) {
            return MoveResult.KEY;
        }

        return MoveResult.SUCCESS;
        
    }

}
