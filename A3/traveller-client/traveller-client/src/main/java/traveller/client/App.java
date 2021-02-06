package traveller.client;

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

    private App() {
    }

    /**
     * Main method.
     *
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {

        // STDIN Input
        /*int defaultPort = 8000;
        String defaultHost = "127.0.0.1";
        String defaultUsername = "Glorifrir Flintshoulder";
        Socket default = new Socket(defaultIP, defaultPort);

        OutputStream outToServer = socket.getOutputStream();
        InputStream inFromServer = socket.getInputStream();*/



        Scanner stdin = new Scanner(System.in);
        StringBuilder inputBuilder = new StringBuilder();
        while (stdin.hasNextLine()) {
            inputBuilder.append(stdin.nextLine());
        }
        stdin.close();

        String inputInitial = inputBuilder.toString();


        List<String> parsedStr = parser(inputBuilder.toString());

        JSONArray inputArr = new JSONArray();

        for (int i = 0; i < parsedStr.size(); i++) {
            String current = parsedStr.get(i);
            JSONObject obj = new JSONObject(current);
            inputArr.put(obj);
        }

        JSONArray output = verify(inputArr);

        System.out.println(output.toString());


    }


    /**
     * Processes a string containing JSON and returns List broken down JSON.
     *
     * @param input Input JSON String to Parse
     * @return Parsed JSON output in List<String>
     */
    private static List<String> parser(String input) {
        // Parse Input
        int openObjects = 0;
        List<String> processd = new ArrayList<String>();
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            if (verbose) {
                System.out.println(i + ":" + input.charAt(i) + ":" + temp.toString());
            }
            // Start of an Object
            if (input.charAt(i) == '{') {
                if (openObjects == 0) {
                    if (temp.length() > 0) {
                        processd.add(temp.toString());
                    }
                    temp = new StringBuilder();
                }
                openObjects++;
                temp.append(input.charAt(i));

                // End of an Object
            } else if (input.charAt(i) == '}') {
                openObjects--;
                temp.append(input.charAt(i));
                if (openObjects == 0) {
                    if (temp.length() > 0) {
                        processd.add(temp.toString());
                    }
                    temp = new StringBuilder();
                }

            } else {
                temp.append(input.charAt(i));
            }
        }

        return processd;
    }

    /**
     * Verifies if JSON Input Commands meets spec.
     * @param arr JSONArray Input.
     * @return Valid JSONArray Output.
     */
    private static JSONArray verify(JSONArray arr) {
        if (arr.length() == 0) {
            throw new IllegalStateException("Empty Input (No network)");
        }

        JSONArray newArr = new JSONArray();

        for (int i = 0; i < arr.length(); i++) {
            JSONObject current;
            String command = "";
            try {
                current = arr.getJSONObject(i);
                command = current.getString("command");
            } catch (JSONException e) {
                throw new IllegalArgumentException("Invalid JSON Input: " + e);
            }
            //System.out.println(current + "num: " + i);
            if (i == 0) {
                if (command.equals("roads")) {
                    newArr.put(current);
                }
                else {
                    throw new IllegalArgumentException("Must have roads command first");
                }
            }
            if (command.equals("roads") && i > 0) {
                throw new IllegalArgumentException("Can't have two roads commands");

            } else if (command.equals("place")) {
                JSONObject params = current.getJSONObject("params");
                String town = params.getString("town");
                // if (!server.network.contains(town)) {
                // System.out.println("Town: " + town + " does not exists");
                // }
                // else {
                newArr.put(current);
                // }
            } else if (command.equals("passage-safe?")) {
                JSONObject params = current.getJSONObject("params");
                String character = params.getString("character");
                String town = params.getString("town");
                // if (!server.townNetwork.contains(town)) {
                // System.out.println("Town: " + town + " does not exists");
                // }
                // else if (!server.townNetwork.contains(character)) {
                // System.out.println("Character: " + character + " has not been placed");
                // }
                // else {
                newArr.put(current);
                // }
            }
        }

        return newArr;
    }
}
