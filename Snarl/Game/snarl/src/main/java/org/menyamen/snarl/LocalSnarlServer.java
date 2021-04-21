package org.menyamen.snarl;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import org.menyamen.snarl.characters.PlayerImpl;
import org.menyamen.snarl.constraints.PlayerScore;
import org.menyamen.snarl.layout.Level;
import org.menyamen.snarl.manage.GameManager;
import static org.menyamen.snarl.util.TestingUtil.processLevelFile;
  
// Server class - multithreaded (creates a new thread connection for each client)
public class LocalSnarlServer {
  
    public static void main(String[] args) throws IOException {
        String levelsFile = "snarl.levels";
        
        //minimum number counter
        int numConnection = 1;
        //number of clients
        int clients = 4;
        //seconds to wait between player connections default
        int wait = 60;
        //port connection
        int port = 4568;
        //observer flag 
        boolean observe = false;
    
        // Command Line Args - parsing through args to convert input strings 
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--levels")) {
                levelsFile = args[i + 1];
            } else if (args[i].equals("--clients")) {
                clients = Integer.parseInt(args[i + 1]);
                if (clients < 1 || clients > 4) {
                    System.out.println("There must be between 1 and 4 clients ");
                    return;
                }
            } else if (args[i].equals("--wait")) {
                wait = Integer.parseInt(args[i + 1]);
 
            } else if (args[i].equals("--observer")) {
                observe = Boolean.parseBoolean(args[i + 1]);;
            }
         
            else if (args[i].equals("--port")) {
                port = Integer.parseInt(args[i + 1]);
            }
        }

        //Initialize the server socket to start listening on given port waiting for client to connect 
        try (ServerSocket serverSocket = new ServerSocket(port)) {
         System.out.println("\n Server ready and listening on port " + port);
            //current time 
             long connectionTime = System.currentTimeMillis();
             //initial timeout 
             boolean timeout = false;

            //running loop for getting client request
            while (true) 
            {
                Socket socket = null;
                try 
                {
                    // socket object to receive incoming client requests by server socket accept 
                    socket = serverSocket.accept();

                    //loop that increments through each client until it reaches max clients or when timeout elapses 
                    if((System.currentTimeMillis() - connectionTime) > wait*1000)
                        timeout = true;
                    if (numConnection <= clients && !timeout) {
                        System.out.println("A new client is connected: Client number =" + numConnection + " description =" + socket);
                        
                        // obtaining input and output streams
                        DataInputStream dis = new DataInputStream(socket.getInputStream());
                        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                        
                        System.out.println("Assigning new thread for this client");
        
                        // create a new thread object for each client 
                        Thread t = new ServerConnection(socket, dis, dos, numConnection, levelsFile, observe, wait);
                        numConnection++;
                        // Invoking the start() method
                        t.start();

                    }else{
                        if(numConnection > clients)
                            System.out.println("limit exceeded");
                        if(timeout)
                            System.out.println("timeout elapsed, client not connected");
                        numConnection--;
                        socket.close();
                    }   
                }
                catch (SocketTimeoutException e){
                    System.out.println("Client has timed out");
                    e.printStackTrace();
                }
                catch (Exception e){
                    socket.close();
                    e.printStackTrace();
                }
            }   
        }
        catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
 
        } 
        catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}
  
// ServerConnection class for each client implementation 
class ServerConnection extends Thread 
{
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
    final String levelsFile;
    int startLevel = 1;
    int numConnection; 
    int playerCount = 2;
    int remoteAdvesariesCount = 1;
    boolean observe = false;
    int wait;

    // Constructor
    public ServerConnection(Socket s, DataInputStream dis, DataOutputStream dos, int numConnection, String levelsFile, boolean observe, int wait)  {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        this.numConnection = numConnection;
        this.levelsFile = levelsFile;
        this.observe = observe;
        this.wait = wait;
    }
    //extends thread, need to override run method
    @Override
    public void run() 
    {
        //stores dis message 
        String received;
        
        while (true) 
        {
            try {
                   //Render the game level 
                   List<Level> levels = processLevelFile(levelsFile);

                    //Create a List of Player scores
                    List<PlayerScore> playerScores = new ArrayList<PlayerScore>();

                    GameManager gameManager = new GameManager(startLevel - 1, levels);
                    //current time 
                    long connectionTime = System.currentTimeMillis();
                    String errorMessage = "";
                    int i = 0;
                    //player count is correct, the time is less than the time out and there is atleast 1 player
                    while((i < playerCount && ((System.currentTimeMillis() - connectionTime) < 30*1000)) || i < 1) {
                        // Ask user for the name of the player
                        dos.writeUTF("Enter Player " + (i + 1) + " Name: ");
                        // receive the answer from client
                        String name = dis.readUTF();
                        //exit the game 
                        if(name.equalsIgnoreCase("Exit"))
                        { 
                            System.out.println("Client " + this.s + " sends exit...");
                            System.out.println("Closing this connection.");
                            dos.writeUTF("Goodbye");
                            this.s.close();
                            this.numConnection --;
                            System.out.println("Connection closed");
                            break;
                        }
                        //register the player (dos is collected and sent to client at the end and then message is outputed)
                        errorMessage += "\n" + gameManager.registerPlayerOnServer(new PlayerImpl(name), playerScores);
                        //move on to the next player 
                        i ++;
                    }

                    dos.writeUTF(errorMessage + "\n" + "Do you wish to register any adversaries?");
                    // receive the answer from client
                    received = dis.readUTF();
                    
                    if(received.equalsIgnoreCase("y"))
                    { 
                        connectionTime = System.currentTimeMillis();
                        errorMessage = "";
                        i = 0;
                        while((i < remoteAdvesariesCount && ((System.currentTimeMillis() - connectionTime) < 30*1000)) || i < 1) {
                            // Ask user for the name of the adversary
                            dos.writeUTF("Enter Adversary " + (i + 1) + " Name:");
                            // receive the answer from client
                            String name = dis.readUTF();
                            // Ask user for the type of the adversary
                            dos.writeUTF("Enter Adversary's type, 'Z' represents a Zombie: 'G' represents a ghost:");
                            // receive the answer from client
                            String type = dis.readUTF();

                            errorMessage += "\n" + gameManager.registerAdversaryOnServer(name, type);
                            i ++;
                        }
                    }
                    
                    
                    //Stores the message until the client has dis 
                    String message = gameManager.startGameOnServer(observe, dis, dos, errorMessage, playerScores);
                      
                    // Ask user what he wants
                    dos.writeUTF(message + "\n" + "Do you wish to play another game? If not, please type exit to quit");
                    
                    // receive the answer from client
                    received = dis.readUTF();
                    
                    if(received.equalsIgnoreCase("Exit"))
                    { 
                        System.out.println("Client " + this.s + " sends exit...");
                        System.out.println("Closing this connection.");
                        this.s.close();
                        this.numConnection --;
                        System.out.println("Connection closed");
                        break;
                    }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
          
        try
        {
            // closing resources
            this.dis.close();
            this.dos.close();
              
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
