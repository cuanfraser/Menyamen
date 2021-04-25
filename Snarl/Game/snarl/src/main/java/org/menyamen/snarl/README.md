# How to use Snarl Server to run Server Client 

To use snarlServer simply run the LocalSnarlServer w/Example file through the launch.json configuration. You will recieve a message from the Server that the server is listening on port 4568.  

Then run the snarlClient by launching LocalSnarlClient w/ Example file which will connect with the server and prompt you to enter the Players name.  

To play the game simply answer y or n (yes or no) whether you want to move your player. Which will send this information back to the server.  

The client and the server’s observer can be used in a terminal. Both will launch external terminals in order to listen to or play the game. To change the observer to false simply change the argument in the launch.json configuration. This will allow the game to toggle between the Observer and Player view.  

For multi-threaded multi-games the server must be started before any clients attempt to start a game.  
To add Remote Adversaries, first register Players and then respond to prompt asking to add remote adversaries with "y".  
Remote Adversaries require both a name and type (Z or G for Zombie or Ghost).  

Enjoy!
