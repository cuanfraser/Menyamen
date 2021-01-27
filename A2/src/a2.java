import java.util.Scanner;

public class a2 {

    public static void main(String[] args) {

        // Command line arguments
        if (args.length > 0) {
            if (args[0].equals("--sum")) {
                // TODO
            }
            else if (args[0].equals("--product")) {
                // TODO
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


        stdin.close();
    }

}