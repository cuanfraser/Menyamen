#SNARL GAME STATES
####FULL STATE - GAME MANAGER 

#####METHODS:
```java
     // Generates map for Level from List of Rooms and List of Hallways if valid
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

    /**
     * Generates the tiles needed for the Hallway.
     * @return Tiles generated.
     * @throws IllegalArgumentException if a waypoint is not vertical/horizontal.
     */
    protected List<Tile> generateTiles() throws IllegalArgumentException
```
#####DATA:
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

