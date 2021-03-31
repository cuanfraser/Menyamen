```java
/**
 * Specifies The interface for SNARL Adversary component which:
 * - interacts with the Game Manager on every turn
 * - gets the full level information (comprised of rooms, hallways and objects) *   at the beginning of a level
 * - An adversary gets an update on all player locations, but only when it’s   *   about to make a turn
 */
public interface Adversary {

    //constants
    private String name;
    private Point pos;
    private List<PlayerImp> players;
    private List<Point> locationPlayers;

  /**
   * Move a single adversary from a given point to another given point. A move 
   * is valid only if the start and end points are valid. 
   * The adversary also receives a list of all of the players and their locations
   *
   * @param start Point to start move.
   * @param end Point to end move.
   * @param playerLocations Locations of each player only accessible when
   * adversary is about to take a turn 
   * @throws IllegalArgumentException if the turn is not possible (a point is not traversable)
   */
   void turn(Point start, Point end, Map<PlayerImp, Point> playerLocations) throws IllegalArgumentException;

    /**
    * Return a boolean that validates or invalidates an adversary based on their username being unique 
    *
    * @param name String username that is to be validated
    * @return boolean if ana dversary is valid or not
    */
   boolean validateUser(String name);

  /**
    * Void function that acts like a switch case that updates the Game Manager *  as the Adversary takes their turn.
    *
    * @return the updated game state
    * @throws IllegalArgumentException if the game state is invalid
    */
   public void update() throws IllegalArgumentException;


}
```