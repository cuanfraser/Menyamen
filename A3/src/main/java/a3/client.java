package a3;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class client {
    public static void main(String[] args) throws IOException {
        //Input scanner from client
        Scanner sc = new Scanner(System.in);
        //Create a connection with port 8000
        Socket s = new Socket("localhost", 8000);
        Scanner sc1 = new Scanner(s.getInputStream());
        System.out.println("Find the sum");

        PrintWriter pr = new PrintWriter(s.getOutputStream());
        pr.println("is it working");
        pr.flush();

        InputStreamReader in = new InputStreamReader(s.getInputStream());
        BufferedReader bf = new BufferedReader(in);

        String str = bf.readLine();
        System.out.println("server :" + str);
    }
}
