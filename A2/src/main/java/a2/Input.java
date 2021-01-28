package a2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class Input {
    private static boolean product = false;
    private static boolean sum = false;
    private static boolean verbose = false;

    public static void main(String[] args) {

        // Command line arguments
        if (args.length > 0) {
            if (args[0].equals("--sum")) {
                sum = true;
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
        if (verbose)
            System.out.println(input);
        int openArrays = 0;
        int openObjects = 0;
        int openStrings = 0;
        List<String> processd = new ArrayList<String>();
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            if (verbose)
                System.out.println(i + ":" + input.charAt(i) + ":" + temp.toString());
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
                // String
            } else if (input.charAt(i) == '"') {
                if (openArrays == 0 && openObjects == 0) {
                    if (temp.length() > 0) {
                        processd.add(temp.toString());
                    }
                    temp = new StringBuilder();

                    if (openStrings == 1) {
                        openStrings--;
                    } else if (openStrings == 0) {
                        openStrings++;
                    }
                }

            } else {
                temp.append(input.charAt(i));
            }
        }
        // Add remaining characters
        if (temp.length() > 0) {
            processd.add(temp.toString());
        }

        if (verbose)
            System.out.println(processd.toString());

        JSONArray output = new JSONArray();

        for (int i = 0; i < processd.size(); i++) {
            String current = processd.get(i);
            JSONObject obj = makeJSON(sum, product, current);
            output.put(obj);
        }

        System.out.println(output.toString());
    }

    /**
     * Converts String parsed from input into JSONObject.
     * 
     * @param sum        Is the programming running from "--sum" command line
     *                   arguments.
     * @param product    Is the programming running from "--product" command line
     *                   arguments.
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
        // Array
        if (jsonString.charAt(0) == '[') {
            JSONArray array = new JSONArray(jsonString);
            outJSON.put("object", array);
            int total = arith(array);
            outJSON.put("total", total);
            return outJSON;

            // Object
        } else if (jsonString.charAt(0) == '{') {
            JSONObject object = new JSONObject(jsonString);
            outJSON.put("object", object);
            int total = arith(object);
            outJSON.put("total", total);
            return outJSON;

            // Integer
        } else {
            if (verbose)
                System.out.println("Else input: " + jsonString);
            int num;
            try {
                num = Integer.parseInt(jsonString);
                outJSON.put("object", num);
                outJSON.put("total", num);
            } catch (NumberFormatException ne) {
                outJSON.put("object", jsonString);
                if (sum)
                    outJSON.put("total", 0);
                if (product)
                    outJSON.put("total", 1);
            } catch (Exception e) {
                throw new IllegalArgumentException("Given Illegal NumJSON in jsonString.");
            }
        }
        return outJSON;
    }

    /**
     * Handles arithmatic for sum and product of NumJSON Values.
     * 
     * @param object The NumJSON Value.
     * @return int of arithmatic for sum or product.
     * @throws IllegalArgumentException object is not a valid NumJSON.
     * @throws RuntimeException         Sum and Product fields both false.
     */
    private static int arith(Object object) throws IllegalArgumentException, RuntimeException {
        if (object instanceof Integer) {
            return (int) object;
        }
        if (object instanceof String) {
            if (sum) {
                return 0;
            } else if (product) {
                return 1;
            } else {
                throw new RuntimeException("Not sum or product");
            }
        }
        if (object instanceof JSONObject) {
            JSONObject json = (JSONObject) object;
            if (json.has("payload"))
                return arith(json.get("payload"));
            else
                return 0;
        }
        if (object instanceof JSONArray) {
            int total = 0;
            JSONArray arr = (JSONArray) object;
            if (sum) {
                for (int i = 0; i < arr.length(); i++) {

                    total += arith(arr.get(i));
                }
            } else if (product) {
                total = 1;
                for (int i = 0; i < arr.length(); i++) {
                    total *= arith(arr.get(i));
                }
            }

            return total;
        } else {
            throw new IllegalArgumentException(
                    "Not valid NumJSON: " + object.toString() + " " + object.getClass().getName());
        }

    }
}