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
        Scanner stdin = new Scanner(System.in);
        StringBuilder inputBuilder = new StringBuilder();
        while (stdin.hasNextLine()) {
            inputBuilder.append(stdin.nextLine());
        }
        stdin.close();

        List<String> parsedStr = parser(inputBuilder.toString());

        JSONArray inputArr = new JSONArray();

        for (int i = 0; i < parsedStr.size(); i++) {
            String current = parsedStr.get(i);
            JSONObject obj = new JSONObject(current);
            inputArr.put(obj);
        }

        //JSONArray output = verify(inputArr);

        System.out.println(inputArr.toString());

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

    private static JSONArray verify(JSONArray arr) {
        if (arr.length() == 0) {
            throw new IllegalStateException("Empty Input (No network)");
        }

        JSONArray newArr = new JSONArray();
        boolean roadsCommandFound = false;

        for (int i = 0; i < arr.length(); i++) {
            JSONObject current;
            String command = "";
            try {
                current = arr.getJSONObject(0);
                command = current.getString("command");
            } catch (JSONException e) {
                throw new IllegalArgumentException("Invalid JSON Input: " + e);
            }
            System.out.println(current);
            if (command.equals("roads")) {
                if (roadsCommandFound) {
                    throw new IllegalArgumentException("Can't have two roads commands");
                }
                else {
                    roadsCommandFound = true;
                    newArr.put(current);
                }
                
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
