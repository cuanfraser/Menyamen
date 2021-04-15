package org.menyamen.snarl;

import java.net.*;
import java.io.*;
import java.util.List;
import java.util.Scanner;

import org.menyamen.snarl.characters.PlayerImpl;
import org.menyamen.snarl.layout.Level;
import org.menyamen.snarl.manage.GameManager;
import static org.menyamen.snarl.util.TestingUtil.processLevelFile;

public class LocalSnarlServer {
    static String levelsFile = "snarl.levels";
    static int startLevel = 1;
    List<PlayerImpl> players;
    static boolean observe = false;

    public static void main(String[] args) throws ClassNotFoundException {
        int clients = 4;
        int wait = 60;
        // String address = "127.0.0.1";
        int port = 4568;

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
                wait = Integer.parseInt(args[i + 1]);
                // TO-DO: reg_timeout
            } else if (args[i].equals("--observe")) {
                observe = true;
            }
            // TO-DO: add address
            // else if(args[i].equals("--address")) {
            // address = (args[i]);
            // }
            else if (args[i].equals("--port")) {
                port = Integer.parseInt(args[i + 1]);
            }
        }

        // Create socket connection

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            // InputStream input = socket.getInputStream();
            // BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();

                System.out.println("New client connected");

                InputStream input = socket.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(input);
                Object obj = ois.readObject();
               // List<PlayerImpl> players;
                /*
                if (obj.getClass() == List.class) {
                    List<PlayerImpl> o = (List<PlayerImpl>)obj;
                if(o != null){
                    players = o.stream()
                    .filter(element->element instanceof PlayerImpl)
                    .map(element->(PlayerImpl)element)
                    .collect(Collectors.toList());
                }
                 }
            */
 
              //  List<PlayerImpl> players = obj.isInstance(List<PlayerImpl> ? obj.cast(List<PlayerImpl>) : null;
                List<PlayerImpl> players = (List<PlayerImpl>)obj;
                if(players != null)
                    startGame(players);
                socket.close();
            }
        }
        catch (ClassNotFoundException ex) {

            System.out.println("Class not found:" + ex.getMessage());
                
        } catch (UnknownHostException ex) {
 
            System.out.println("Server not found: " + ex.getMessage());
 
        } catch (IOException ex) {
 
            System.out.println("I/O error: " + ex.getMessage());
        }
    }
   

    public static void startGame(List<PlayerImpl> players) throws IllegalArgumentException{
        List<Level> levels = processLevelFile(levelsFile);

        GameManager gameManager = new GameManager(startLevel - 1, levels);
    
        Scanner input = new Scanner(System.in);
    
        for (PlayerImpl p : players) {
            gameManager.registerPlayer(p);
        }
       
        gameManager.startGame(input, observe);
        input.close();       
    }

}
