package org.menyamen.snarl;

import java.io.*;
import java.util.Scanner;
import java.io.IOException;
import java.net.Socket;
  
// Client class
public class LocalSnarlClient 
{
    private static String address = "127.0.0.1";
    private static int port = 4568;
    
  
    public static void main(String[] args) throws IOException 
    {
        for (int i = 0; i < args.length; i++) {
            if(args[i].equals("--address")) {
                address = (args[i + 1]);
            }
            else if(args[i].equals("--port")) {
                port = Integer.parseInt(args[i + 1]);
            }
        }
        
        try
        {
            Scanner scn = new Scanner(System.in);
              
            // Create a connection with server
            Socket s = new Socket(address, port);
            System.out.println("Connected to server on: " + "address=" + address + " port=" + port);
      
            // obtaining input and out streams
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
      
            // the following loop performs the exchange of
            // information between client and server
            while (true) 
            {
                System.out.println(dis.readUTF());
                String dataToSend = scn.nextLine();
                 
                // If client sends exit,close this connection 
                // and then break from the while loop
                if(dataToSend.equals("Exit"))
                {
                    System.out.println("Closing this connection : " + s);
                    s.close();
                    System.out.println("Connection closed");
                    break;
                }
                  
                dos.writeUTF(dataToSend);
             
            }
            // closing resources
            scn.close();
            dis.close();
            dos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}