# SNARL GAME STATES  
#### FULL STATE - GAME MANAGER   
Here are the methods and data that will be available to the full state Game Manager. The full state will be able to access a list of players, a list of rooms, a list of hallways, a list of adversaries, the game object locations (key and exit), all the levels, the cartesian location of every room, and the location of every player or adversary. The game manager will act as a sort of server for our game and hold all the data necessary to initiate the game, validate a list of players, place the game objects, place the adversaries, place the players, generate the tiles to create the board of rooms and hallways.  
##### METHODS:  
```java
     // Generates map for Level from List of Rooms and List of Hallways if valid, palces players, adversaries, and game objects (key and exit)
     void generateLevel() 
    
     /**
      * Checks if Room has a valid placement in the current Level.
      * @param room Room to test validity.
      * @return True if valid, false if invalid.
      */
     public boolean validRoomPlacement(Room room)
    
     /**
      * Add a player to the game
      * @param Player to add
      * @return List of players
      */
     private List<Player> addPlayer(Player player)

     /**
      * Removes a player from the game
      * @param Player to remove 
      * @return List of players
      */
     private List<Player> removePlayer(Player player)

    /**
     * Checks if Hallway has a valid placement in current Level.
     * @param hallway Hallway to test.
     * @return True if valid, false if invalid.
     */
    public Boolean validHallwayPlacement(Hallway hallway)

     // Ends the game if either the player or adversary wins
     private void endGame() 
```
##### DATA:  
```java
    - List of rooms (World generation)
    - List of hallways
    - Location of rooms 
    - Location of key 
    - Location of exit 
    - Current level 
    - Total number of levels
    - List of players in order 
    - List of adversaries
    - Number of players alive
    - Number of adversaries in a room 
    - Number of players in a room 
    - Specific location of board (cartesian point) 
    - Specific position of player in a room
    - Specific position of adversary in a room
```
#### LIMITED STATE - PLAYER 
Unlike the Full State in the Game Manager the Limited Player State has less data access and less control over the game. The player can only view the tiles immediately in its vicinity meaning the tiles immediately upward, downward, to the left, or the right of the tile the player is currently on. The player will also know which level it is on but not have access to all levels. The player will know it's own location and how many other players are still in the game. 
##### METHODS: 
```java
    /**
     * If a player runs into an adversary the player gets removed from the game, sends to the full state which removes the player from the game
     * @param Player to get expelled
     */
    public void getExpelled(Player player)

    /**
     * Removes the key if found by a player
     * @param The Player who just moved and the Key that was found
     */
    public void findKey(Player player, Key key)

    /**
     * Exits level if player reaches portal with the key
     * @param Player that is exiting
     */
    public void findExit(Player player)

    /**
     * Allows a player to move up, down, right, or left.
     * @param Player player
     */
    public void Move(Player player)

    /**
     * If a player reaches the last exit portal in the last level then they have exited the dungeon and won
     * @param Player to win
     */
    public void Win(Player player)

    /**
     * If a player is the last to get expelled then the adversaries have won (all players get expelled in the current level)
     * @param Player to lose
     */
    public void Lose(Player player)
```
##### DATA:  
```java
    - Tiles directly in vicinity of player (x + 1, x - 1, y + 1, y - 1)
    - Current level the player is in
    - Number of players alive
    - Specific position of player in a room
```
#### LIMITED STATE - ADVERSARY 
In addition to the Limited Player State the Adversary state also is limited in comparison to the full game manager state. The adversary has fewer data access points and less control over the game. The adversary can see the number of rooms, hallways, and players alive in the current level. Adversaries can see the specific position of a player in a room, the specific position of an adversary in a room, the location of the key and exit portal. They can expel a player if they move onto the same tile as the player or vice versa. They can also move up, down, right, or left by one tile (no diagonal moves). They can win if they expel all players in the current level. They can lose if a player reaches the last exit portal in the last level of the game.                                                                                                                                                                                                                                                                                           
##### METHODS: 
```java
    /**
     * If a player runs into an adversary the player gets removed from the current level, but is added to the next level if it exists. 
     * @param Player to get expelled
     */
    public void expelPlayer(Player player)

    /**
     * Allows an adversary to move up, down, right, or left.
     * @param Adversary adversary
     */
    public void Move(Adversary adversary)

    /**
     * If all players are expelled in the current level then the Adversaries win.
     * @param Adversary adversary to win
     */
    public void Win(Adversary adversary)

    /**
     * If a player gets to the last exit portal in the last level then the Adversary loses
     * @param Adversary to lose
     */
    public void Lose(Adversary adversary)
```
##### DATA:  
```java
    - Number of rooms 
    - Number of hallways 
    - Current level
    - Number of players alive
    - Number of adversaries in a room 
    - Number of players in a room 
    - Specific position of player in a room
    - Specific position of adversary in a room
    - Location of key 
    - Location of exit portal
```