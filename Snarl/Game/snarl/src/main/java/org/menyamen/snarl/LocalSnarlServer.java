package org.menyamen.snarl;


import java.io.*;
import java.net.*;
import java.util.List;
import org.menyamen.snarl.characters.PlayerImpl;
import org.menyamen.snarl.layout.Level;
import org.menyamen.snarl.manage.GameManager;
import static org.menyamen.snarl.util.TestingUtil.processLevelFile;
  
// Server class
public class LocalSnarlServer 
{
  
    public static void main(String[] args) throws IOException 
    {
        String levelsFile = "snarl.levels";
        int max = 3;
        int numConnection = 1;
        int clients = 4;
        String wait = "60";
        // String address = "127.0.0.1";
        int port = 4568;
        boolean observe = false;
    
        // Command Line Args
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
                wait = args[i + 1];
 
            } else if (args[i].equals("--observer")) {
                observe = Boolean.parseBoolean(args[i + 1]);;
            }
            // TO-DO: add address
            // else if(args[i].equals("--address")) {
            // address = (args[i]);
            // }
            else if (args[i].equals("--port")) {
                port = Integer.parseInt(args[i + 1]);
            }
        }

        try (ServerSocket ss = new ServerSocket(port)) {
         System.out.println("\n Server ready and listening on port " + port);
        // running loop for getting
        // client request
            while (true) 
            {
                Socket s = null;
                try 
                {
                    // socket object to receive incoming client requests
                    s = ss.accept();
                    if (numConnection < max) {
                        System.out.println("A new client is connected: Client number =" + numConnection + " description =" + s);
                        
                        // obtaining input and out streams
                        DataInputStream dis = new DataInputStream(s.getInputStream());
                        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                        
                        System.out.println("Assigning new thread for this client");
        
                        // create a new thread object
                        Thread t = new ServerConnection(s, dis, dos, numConnection, levelsFile, observe, wait);
                        numConnection++;
                        // Invoking the start() method
                        t.start();
                    }else{
                        numConnection--;
                        s.close();
                        System.out.println("limit exceeded");
                    }
                    
                }
                catch (Exception e){
                    s.close();
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
  
// ServerConnection class
class ServerConnection extends Thread 
{
     final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
    final String levelsFile;
    int startLevel = 1;
    int numConnection; 
    int playerCount = 1;
    boolean observe = false;
    String wait;

    // Constructor
    public ServerConnection(Socket s, DataInputStream dis, DataOutputStream dos, int numConnection, String levelsFile, boolean observe, String wait) 
    {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        this.numConnection = numConnection;
        this.levelsFile = levelsFile;
        this.observe = observe;
        this.wait = wait;
    }
  
    @Override
    public void run() 
    {
        String received;
  
        while (true) 
        {
            try {
                    /*   
                    while(dis.available()==0)
                    {
                        try {
                            Thread.sleep(Long.parseLong(wait));//sleep if there is no data coming
                        } catch (InterruptedException e) {
                        
                            e.printStackTrace();
                        }
                    }
                    */
                   List<Level> levels = processLevelFile(levelsFile);

                    GameManager gameManager = new GameManager(startLevel - 1, levels);
                    String result = "";
                    for (int i = 0; i < playerCount; i++) {
                        // Ask user for the name of the player
                        dos.writeUTF("Enter Player " + (i + 1) + " Name: ");
                        // receive the answer from client
                        String name = dis.readUTF();
                     
                        result += "\n" + gameManager.registerPlayerOnServer(new PlayerImpl(name), playerCount);
                     
                    }

                    String message = gameManager.startGameOnServer(observe, dis, dos, result);
                      
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
