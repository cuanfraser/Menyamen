package org.menyamen.snarl;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.menyamen.snarl.characters.PlayerImpl;

public class LocalSnarlClient {
    private static String address = "127.0.0.1";
    private static int port = 4568;
    private static String session;
    private static int playerCount = 3;

    public LocalSnarlClient() {
    }

    /**
     * Main method.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {

        for (int i = 0; i < args.length; i++) {
            if(args[i].equals("--address")) {
                address = (args[i + 1]);
            }
            else if(args[i].equals("--port")) {
                port = Integer.parseInt(args[i + 1]);
            }
        }

        try {
            // Startup

            // Create a connection with port
            Socket sock = new Socket(address, port);
            System.out.println("Connected!");
            // Open input/output streams
            //PrintStream outStream = new PrintStream(sock.getOutputStream());
            //BufferedReader inStream = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            OutputStream outputStream = sock.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            // [sign-up players]
            List<PlayerImpl> players = new ArrayList<PlayerImpl>();
            Scanner input = new Scanner(System.in);

            for (int i = 0; i < playerCount; i++) {
                System.out.print("Enter Player " + (i + 1) + " Name: ");
                String name = input.nextLine();
                players.add(new PlayerImpl(name));
            }

            objectOutputStream.writeObject(players);
            input.close();
            sock.close();
            
        } catch (UnknownHostException ex) {
     
            System.out.println("Server not found: " + ex.getMessage());
     
        } catch (IOException ex) {
     
            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}
