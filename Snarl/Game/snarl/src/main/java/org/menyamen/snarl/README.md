#How to Play Snarl 

Our game can be played in a terminal locally on your machine. These are the rules to follow in order to play our Snarl game:  
  
1) To start the game you must register your players with unique names, you can register at most 4 players. The Player will display on the level as the character 'P'. 
  
2) There are two types of adversaries that will try to expel your players: a ghost or a zombie. Each level will incremently add zombies and ghosts to the level. A ghost is represented by the character 'G' and the zombie is represented by the character 'Z'.  
    
3) Once the players are registered the level of your choice will be initialized, The level consists of open tiles ('.'), walls ('+'), doors ('/'), a key ('K'), and an exit portal ('O').  
  
4) The goal for each player is to reach the key ('K') which unlocks the exit portal ('O') and reach the exit portal before a zombie or ghost catches you. Keep in mind that even though zombies can not leave the room they are spawned in the ghost is able to teleport through walls and respwawn in another room at random.  
  
5) Once the player reaches the unlocked exit portal they will win the current level and move on to the next.  
  
6) If a zombie or ghost catches you before you reach the exit portal then your player will be expeled. The player can only come back if another player gets to the exit portal and wins the level. In which case all players come back to play the next level. 
  
7) If the adversaries expel all of the players in a level then the game is over and the players have lost.  
  
8) After players have registered each player will receive a turn where they are prompted to enter a row and column to which they wish to move to. They will only be allowed to move in up to two cardinal positions (up, right, down, left) onto a traversable tile. A traversable tile for a player is an open tile ('.') that may contain a game object (key or portal) but can not contain another character (player, zombie or ghost).  
  
9) Both zombies and ghosts can move only one cardinal move, they move by automated programming done by the computer, they will always chase a player within its vicinity.  
  
10) Zombies can not move onto door tiles or wall tiles so they will always remain in the same room and chase players within the same room as them. Ghosts however can move onto door tiles, hallway tiles, and even wall tiles. If a ghost enters a wall it will be teleported to another room at random. Ghosts will always move towards the closest player regardless of which room it is in.  
  
11) If the players win the last level in the list of all levels then the players have won the game and the game is over. If the adversaries expel all of the players in any level then the adverasaries have won the game and the game is over. 

