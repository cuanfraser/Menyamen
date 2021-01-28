package a2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class Input {

    public static void main(String[] args) {

        boolean product = false;
        boolean sum = false;

        // Command line arguments, sum or product must be an argument entered
        if (args.length > 0) {
            if (args[0].equals("--sum")) {
                product = true;
            } else if (args[0].equals("--product")) {
                product = true;
            } else {
                System.err.println("Must include command line arguments of '--sum' or '--product'");
                System.exit(-1);
            }
        } else {
            System.err.println("Must include command line arguments of '--sum' or '--product'");
            System.exit(-1);
        }

        // STDIN Input
        Scanner stdin = new Scanner(System.in);
        StringBuilder inputBuilder = new StringBuilder();
        while (stdin.hasNext()) {
            inputBuilder.append(stdin.next());
        }
        stdin.close();

        // Parse Input
        String input = inputBuilder.toString();
        System.out.println(input);
        int openArrays = 0;
        int openObjects = 0;
        List<String> processd = new ArrayList<String>();
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            // System.out.println(i + ":" + input.charAt(i) + ":" + temp.toString());

            // Start of an Array
            if (input.charAt(i) == '[') {
                if (openArrays == 0 && openObjects == 0) {
                    if (temp.length() > 0) {
                        processd.add(temp.toString());
                    }
                    temp = new StringBuilder();
                }
                openArrays++;
                temp.append(input.charAt(i));

                // End of an Array
            } else if (input.charAt(i) == ']') {
                openArrays--;
                temp.append(input.charAt(i));
                if (openArrays == 0 && openObjects == 0) {
                    if (temp.length() > 0) {
                        processd.add(temp.toString());
                    }
                    temp = new StringBuilder();
                }

                // Start of an Object
            } else if (input.charAt(i) == '{') {
                if (openArrays == 0 && openObjects == 0) {
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
                if (openArrays == 0 && openObjects == 0) {
                    if (temp.length() > 0) {
                        processd.add(temp.toString());
                    }
                    temp = new StringBuilder();
                }
                // General Character
            } else {
                temp.append(input.charAt(i));
            }
        }
        // Add remaining characters
        if (temp.length() > 0) {
            processd.add(temp.toString());
        }

        System.out.println(processd.toString());

        JSONArray output = new JSONArray();

        for (int i = 0; i < processd.size(); i++) {
            String current = processd.get(i);
            JSONObject obj = makeJSON(sum, product, current);
            output.put(obj);
        }

        System.out.println(output.toString());

        // Scanner tokeniser = new Scanner(input).useDelimiter("(?=\\[)|(?<=\\])");
        // while (tokeniser.hasNext()) {
        // System.out.println("new" + tokeniser.next());
        // }
        // tokeniser.close();

    }

    /**
     * Converts String parsed from input into JSONObject.
     * @param sum Is the programming running from "--sum" command line arguments.
     * @param product Is the programming running from "--product" command line arguments.
     * @param jsonString The String from input to be parsed.
     * @return JSONObject from input string.
     * @throws IllegalArgumentException
     */
    private static JSONObject makeJSON(boolean sum, boolean product, String jsonString)
            throws IllegalArgumentException {
        JSONObject outJSON = new JSONObject();

        if (jsonString.length() <= 0) {
            throw new IllegalArgumentException("Given empty jsonString.");
        }
        if (jsonString.charAt(0) == '[') {
            //TODO

        } else if (jsonString.charAt(0) == '{') {
            //TODO

        } else {
            int num;
            try {
                num = Integer.parseInt(jsonString);
            } catch (Exception e) {
                throw new IllegalArgumentException("Given Illegal jsonString.");
            }
            outJSON.append("object", num);
            outJSON.append("payload", num);
        }
        return outJSON;
    }

}