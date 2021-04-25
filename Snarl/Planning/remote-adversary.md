# Remote Adversaries

Remote Adversaries can be registed by the clients after the Players have been registered. A prompt will ask the client user if they would like to register remote adversaries (number based on level count), and then after providing names and types for each Adversary (Ghost or Zombie), the game can begin.  
Remote Adversaries will complete their moves BEFORE regular local adversaries.  

The RemoteAdversary Abstract class extends a the regular Abstract Class for an Adversary.  
RemoteZombie and RemoteGhost then extend RemoteAdversary.