package traveller.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Main class.
 */
public final class App {
    private static boolean verbose = false;
    private App() {
    }

    /**
     * Main method.
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

        System.out.println(inputArr.toString());

    }

    /**
     * Processes a string containing JSON and returns List broken down
     * JSON
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
            if (verbose)
                System.out.println(i + ":" + input.charAt(i) + ":" + temp.toString());
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

}
