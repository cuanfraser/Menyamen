package org.menyamen.snarl.characters;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.awt.Point;

import org.menyamen.snarl.constraints.Move;

//multiple inheritance 
public abstract class RemoteAdversary extends Adversary{
    
    protected RemoteAdversary(Point pos) {
        super(pos);
       
    }

    protected RemoteAdversary(String name) {
        super(name);
       
    }

    //Static method that creates a remote ghost or remote zombie depending on input from user 
    public static RemoteAdversary CreateAdversary(String name, String type) {
        if(type.equalsIgnoreCase("g"))
            return new RemoteGhost(name);
        else if(type.equalsIgnoreCase("z"))
            return new RemoteZombie(name);
        return null;
    }
    
    
    public Move adversaryMoveOnServer(DataInputStream dis, DataOutputStream dos, String name, Point pos, String messages) throws IOException {
        
        String  s = messages + "\n" + "Would you like to move your adversary, " + name + "'s position? (please enter Y or N)";
        dos.writeUTF(s);
        String input = dis.readUTF();
        while (!input.equalsIgnoreCase("N") && !input.equalsIgnoreCase("Y")) {
            dos.writeUTF("not a valid response." + "\n" + "Would you like to move your adversary, " + name + "'s position? (please enter Y or N)");
            input = dis.readUTF(); 
        }

        //Stays put in current position -> return current pos
        if (input.equalsIgnoreCase("N")) {
            return new Move(pos);
        }
       
        int row = 0;
        int col = 0;
        boolean validRow = false;
        String message = "";
        while(!validRow){
            try {
                dos.writeUTF(message + "\n" + "Enter the row you would like to move to "); 
                row = Integer.parseInt(dis.readUTF());
                validRow = true;
                message = "";
            }
            catch(NumberFormatException e){
                 validRow = false;
                 message = "not a valid response.";
            }
        }
        boolean validCol = false;
        
        while(!validCol){
            try {
                dos.writeUTF(message + "\n" + "Enter the column you would like to move to "); 
                col = Integer.parseInt(dis.readUTF());
                validCol = true;
            }
            catch(NumberFormatException e){
                validCol = false;
                message = "not a valid response.";
            }
        }
            
        Point newPos = new Point(col, row);

        return new Move(newPos); 
    
    }  
}
