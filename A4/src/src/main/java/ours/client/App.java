package ours.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Main class.
 */
public final class App {
    private static boolean verbose;
    private static String ip = "127.0.0.1";
    private static int port = 8000;
    private static String username = "Glorifrir Flintshoulder";
    private static String session;

    private App() {
    }

    /**
     * Main method.
     * 
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {

        // Command line arguments
        if (args.length >= 0 && args.length <= 3) {
            if (args.length > 0) {
                ip = args[0];
            }
            if (args.length > 1) {
                try {
                    port = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    System.err.println("Port must be an integer");
                    System.err.println("usage: ./a4 <ip> <port> <username>");
                    System.exit(-1);
                }
            }
            if (args.length > 2) {
                username = args[2];
            }
        } else {
            System.err.println("usage: ./a4 <ip> <port> <username>");
            System.exit(-1);
        }

        try {
            // Startup

            // Create a connection with port
            Socket sock = new Socket(ip, port);
            // Open input/output streams
            PrintStream outStream = new PrintStream(sock.getOutputStream());
            BufferedReader inStream = new BufferedReader(new InputStreamReader(sock.getInputStream()));

            // [sign-up name]
            outStream.println(username);
            // receive [session id]
            session = inStream.readLine();

            Scanner stdin = new Scanner(System.in);
            String createString = stdin.nextLine();
            JSONObject create = makeCreateRequest(createString);
            if (verbose) {
                System.out.println(create.toString());
            }
            outStream.println(create);

            // TODO: FROM HERE

            // Processing Phase
            while (stdin.hasNextLine()) {
                String line = stdin.nextLine();
                String received = inStream.readLine();
                System.out.println(received);

            }
            stdin.close();

            // Shutdown

            outStream.close();
            inStream.close();
            sock.close();

        } catch (UnknownHostException e) {
            System.err.println("Unknown Host");
            System.exit(-1);
        } catch (IOException ie) {
            System.err.println("IO Exception");
            ie.printStackTrace();
            System.exit(-1);
        }

    }

    /**
     * Verifies if JSON Input Command meets spec.
     * 
     * @param input String of JSON Input to verify
     * @return Valid JSONObject Output.
     * @throws IllegalArgumentException
     */
    private static JSONObject makeCreateRequest(String input) throws IllegalArgumentException{
        JSONObject inputObject;
        JSONObject outputObject;
        try {
            inputObject = new JSONObject(input);
        } catch (JSONException e) {
            JSONObject error = new JSONObject();
            error.put("error", "not a request");
            error.put("object", input);
            System.err.println(error.toString());
            throw new IllegalArgumentException("Invalid JSON Input: " + e);
        }
        try {
            if (inputObject.getString("command").equals("roads")) {

                // Create list of towns
                List<String> towns = new ArrayList<String>();
                JSONArray params = inputObject.getJSONArray("params");
                for (int i = 0; i < params.length(); i++) {
                    JSONObject current = params.getJSONObject(i);
                    String from = current.getString("from");
                    String to = current.getString("to");
                    if (!towns.contains(from)) {
                        towns.add(from);
                    }
                    if (!towns.contains(to)) {
                        towns.add(to);
                    }
                }

                outputObject = new JSONObject();
                outputObject.put("towns", towns.toArray());
                outputObject.put("roads", inputObject.getJSONObject("params"));
            }
            else throw new IllegalArgumentException("First command not 'roads'");
        } catch (JSONException e) {
            throw new IllegalArgumentException("First request should be a well-formed Create request");
        }
        return outputObject;
    }
}
