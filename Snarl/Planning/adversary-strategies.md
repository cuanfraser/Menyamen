```java
/**
 * Specifies the strategies for determining the next move of adversaries 

 */
  
  //Ghost Strategies: 

  /**
   * Move a single ghost in one cardinal direction from a traversable tile to * another traversable tile that moves it closer to a player. 
   * Receive a list of all the players locations
   * Skip if no valid cardinal moves.
   * If a ghost enters a wall tile it teleports to a random room. 
   * Ghosts can enter doors and move through hallways.
   * 
   *
   * @param start Point to start move.
   * @param end Point to end move.
   * @param state Locations of each player only accessible when
   * ghost is about to take a turn 
   */
   void turn(Point start, Point end, PlayerState state);

     //Zombie Strategies: 

  /**
   * Move a single zombie in one cardinal direction from a traversable tile to * another traversable tile that moves it closer to a player. 
   * Receive a list of all the players locations
   * Skip if no valid cardinal moves.
   * Zombies cannot enter doors and move through hallways.
   * 
   *
   * @param start Point to start move.
   * @param end Point to end move.
   * @param state Locations of each player only accessible when
   * zombie is about to take a turn 
   */
   void turn(Point start, Point end, PlayerState state);



```