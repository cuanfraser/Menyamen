# Multi-game Server

Our Snarl Multi-game Server allows multiple games to be run at the same time, with different clients able to connect to different games. The rules are the same as Milestone 9, and we actively check for timeouts when registering players.  
The games are run on a multi-threaded server, meaning they are run in parallel and will all work normally as they would with only one game running.  
The server keeps track of the leaderboard for each game and tracks the players' scores (Player class).  
The games can be ended via the console and clients may type in "Exit" to stop their game.  
