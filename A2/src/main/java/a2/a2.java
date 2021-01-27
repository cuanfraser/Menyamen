package a2;

import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;


public class a2 {

    public static void main(String[] args) {

        int sum;

        // Command line arguments
        if (args.length > 0) {
            if (args[0].equals("--sum")) {
                sum = 1;
            }
            else if (args[0].equals("--product")) {
                sum = 0;
            }
            else {
                System.err.println("Must include command line arguments of '--sum' or '--product'");
                System.exit(-1);
            }
        }
        else {
            System.err.println("Must include command line arguments of '--sum' or '--product'");
            System.exit(-1);
        }
        
        Scanner stdin = new Scanner(System.in);

        StringBuilder inputBuilder = new StringBuilder();
        while (stdin.hasNextLine()) {
            inputBuilder.append(stdin.nextLine());
        }

        System.out.println(inputBuilder.toString());
        
        stdin.close();
    }

}