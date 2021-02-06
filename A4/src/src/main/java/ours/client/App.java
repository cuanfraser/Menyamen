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

            // Processing Phase

            List<JSONObject> characters = new ArrayList<JSONObject>();

            while (stdin.hasNextLine()) {
                String line = stdin.nextLine();

                JSONObject testObj = new JSONObject(line);
                if (testObj.has("command")) {
                    if (testObj.getString("command").equals("passage-safe?")) {
                        JSONObject batch = makeBatchRequest(characters, testObj);
                        outStream.println(batch);
                        characters = new ArrayList<JSONObject>();
                    }
                    else if (testObj.getString("command").equals("place")) {
                        characters.add(testObj);
                    }

                } else {
                    JSONObject error = new JSONObject();
                    error.put("error", "not a request");
                    error.put("object", testObj);
                    System.err.println(error.toString());
                    System.exit(-1);
                }

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
    private static JSONObject makeCreateRequest(String input) throws IllegalArgumentException {
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
            } else
                throw new IllegalArgumentException("First command not 'roads'");
        } catch (JSONException e) {
            throw new IllegalArgumentException("First request should be a well-formed Create request");
        }
        return outputObject;
    }

    /**
     * Makes a batch request using a list of place commands and a passage-safe command.
     * @param chars List of place commands as JSONObjects.
     * @param safe Passage-safe command as JSONObject.
     * @return Batch request as JSONObject.
     */
    private static JSONObject makeBatchRequest(List<JSONObject> chars, JSONObject safe) {
        JSONObject output = new JSONObject();
        List<JSONObject> newChars = new ArrayList<JSONObject>();

        for (int i = 0; i < chars.size(); i++) {
            JSONObject current = chars.get(i);
            JSONObject params = current.getJSONObject("params");
            JSONObject character = new JSONObject();
            character.put("name", params.getString("character"));
            character.put("town", params.getString("town"));
            newChars.add(character);            
        }

        output.put("characters", newChars.toArray());

        JSONObject query = new JSONObject();
        query.put("character", safe.getString("character"));
        query.put("destination", safe.getString("town"));
        output.put("query", query);

        return output;

    }
}
