# How to use Snarl Server to run Server Client 

To use snarlServer simply run the LocalSnarlServer w/Example file through the launch.json configuration. You will recieve a message from the Server that the server is listening on port 4568.  

Then run the snarlClient by launching LocalSnarlClient w/ Example file which will connect with the server and prompt you to enter the PLayers name.  

To play the game simply answer y or n (yes or no) whether you want to move your player. Which will send this information back to the server.  

The client and the server’s observer can be used in a terminal. Both will launch external terminals in order to listen or play the game. To change the observer to false simply change the argument in the launch.json configuration for the Server and Client. This will allow the game to toggle between the Observer and Player view.  

Enjoy! 
