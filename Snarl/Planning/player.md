```java
/**
 * Specifies The interface for a Player component which:
 * - allows a player to move 2 grid units away in any cardinal or diagonal direction
 * - determines the end of a level versus the end of the game
 * - knows when to reject invalid game states. 
 */
public interface RuleChecker {

    //constants
    private String name;
    private Boolean isExpelled;
    private Point pos;

  /**
   * Move a single player from a given point to another given point. A move is valid only if
   * the start and end points are valid. A player can move to any traversable tile up to 
   * 2 cardinal moves away from themselves (up, down, left, or right). The player can also opt to
   * stay put by making the start and end point the same tile. 
   *
   * @param start Point to start move.
   * @param end Point to end move.
   * @throws IllegalArgumentException if the move is not possible (a point is not traversable)
   */
  void move(Point start, Point end) throws IllegalArgumentException;

  /**
    * Void function that acts like a switch case that updates the Game Manager as the Player makes moves.
    *
    * @return the updated game state
    * @throws IllegalArgumentException if the game state is invalid
    */
   public void update() throws IllegalArgumentException;

}
```