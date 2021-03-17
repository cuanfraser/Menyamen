```java
/**
 * Specifies The interface for the Rule Checker which:
 * - validates the movements and interactions from players and adversaries 
 * - determines the end of a level versus the end of the game
 * - knows when to reject invalid game states. 
 */
public interface RuleChecker {

  /**
   * Move a single player from a given point to another given point. A move is valid only if
   * the start and end points are valid. Specific implementations may place additional constraints
   * on the validity of a move (Player vs Adversary). A player can move to any traversable tile up to 
   * 2 cardinal moves away from themselves (up, down, left, or right).
   *
   * @param start Point to start Hallway.
   * @param end Point to end Hallway.
   * @throws IllegalArgumentException if the move is not possible (a point is not traversable)
   */
  void move(Point start, Point end) throws IllegalArgumentException;

  /**
   * Determine and return if the game is over or not. A game is over if all Players are ejected 
   * or if any Player finds the Key and Exit Portal on the final Level.
   *
   * @return true if the game is over, false otherwise
   */
  boolean isGameOver();

  /**
   * Determine and return if the level is over or not. A level is over a Player finds the Key and reaches 
   * the Exit Portal.
   *
   * @return true if the level is over, false otherwise
   */
  boolean isLevelOver();

  /**
   * Return a string (from enum value) that represents the current state of the board. 
   *
   * @return the game state as a string
   * @throws IllegalStateException if the game state is invalid
   */
  String getGameState() throws IllegalStateException;

}
```