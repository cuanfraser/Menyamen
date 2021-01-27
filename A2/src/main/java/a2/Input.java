package a2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class Input {

    public static void main(String[] args) {

        int product;

        // Command line arguments
        if (args.length > 0) {
            if (args[0].equals("--sum")) {
                product = 0;
            } else if (args[0].equals("--product")) {
                product = 1;
            } else {
                System.err.println("Must include command line arguments of '--sum' or '--product'");
                System.exit(-1);
            }
        } else {
            System.err.println("Must include command line arguments of '--sum' or '--product'");
            System.exit(-1);
        }

        Scanner stdin = new Scanner(System.in);

        StringBuilder inputBuilder = new StringBuilder();
        while (stdin.hasNext()) {
            inputBuilder.append(stdin.next());
        }
        stdin.close();

        String input = inputBuilder.toString();
        System.out.println(input);

        int openArrays = 0;
        int openObjects = 0;
        List<String> processd = new ArrayList<String>();
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            // System.out.println(i + ":" + input.charAt(i) + ":" + temp.toString());
            if (input.charAt(i) == '[') {
                if (openArrays == 0 && openObjects == 0) {
                    if (temp.length() > 0) {
                        processd.add(temp.toString());
                    }
                    temp = new StringBuilder();
                }
                openArrays++;
                temp.append(input.charAt(i));

            } else if (input.charAt(i) == ']') {
                openArrays--;
                temp.append(input.charAt(i));
                if (openArrays == 0 && openObjects == 0) {
                    if (temp.length() > 0) {
                        processd.add(temp.toString());
                    }
                    temp = new StringBuilder();
                }

            } else if (input.charAt(i) == '{') {
                if (openArrays == 0 && openObjects == 0) {
                    if (temp.length() > 0) {
                        processd.add(temp.toString());
                    }
                    temp = new StringBuilder();
                }
                openObjects++;
                temp.append(input.charAt(i));

            } else if (input.charAt(i) == '}') {
                openObjects--;
                temp.append(input.charAt(i));
                if (openArrays == 0 && openObjects == 0) {
                    if (temp.length() > 0) {
                        processd.add(temp.toString());
                    }
                    temp = new StringBuilder();
                }
            } else {
                temp.append(input.charAt(i));
            }
        }
        if (temp.length() > 0) {
            processd.add(temp.toString());
        }

        System.out.println(processd.toString());

        JSONObject obj = new JSONObject();

        // Scanner tokeniser = new Scanner(input).useDelimiter("(?=\\[)|(?<=\\])");
        // while (tokeniser.hasNext()) {
        // System.out.println("new" + tokeniser.next());
        // }
        // tokeniser.close();

    }

}