```java
/**
 * Specifies The interface for the Game Manager which:
 * - validates and accepts players with unique name to the game 
 * - and starts a game with a single level, which will be provided. 
 */
public interface GameManager {

   /**
   * Void function that starts the game with a single level
   *
   * @param players List of valid players registered with unique names
   * @param level The single level at which the game starts
   * @throws IllegalArgumentException if the level is not possible or a Player has an invalid name
   */
  public void startGame(List<Player> players, Level level) throws IllegalArgumentException;

   /**
   * Return a boolean that validates or invalidates a player based on their username being unique 
   *
   * @return boolean if a player is valid or not
   */
  boolean validatePlayer(Player player);

   /**
   * Void function that acts like a switch case that updates the game as the Player makes moves.
   *
   * @return the updated game state
   * @throws IllegalArgumentException if the game state is invalid
   */
  public void update() throws IllegalArgumentException;


}
```