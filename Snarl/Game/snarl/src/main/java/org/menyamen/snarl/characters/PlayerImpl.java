package org.menyamen.snarl.characters;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.awt.Point;
import java.util.Scanner;

import org.menyamen.snarl.constraints.Move;
import org.menyamen.snarl.state.PlayerState;

import static org.menyamen.snarl.util.TestingUtil.toRowCol;

public class PlayerImpl implements Player {
    /**
     *
     */
    private String name;
    private Point pos;
    private boolean isExpelled = false;

    public PlayerImpl(String name) {
        this.name = name;
    }

    public PlayerImpl(String name, Point pos) {
        this(name);
        this.pos = pos;
    }

    public PlayerImpl(String name, int x, int y) {
        this(name);
        this.pos = new Point(x, y);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Point getPos() {
        return this.pos;
    }

    @Override
    public void setPos(Point pos) {
        this.pos = pos;
    }

    @Override
    public boolean getIsExpelled() {
        return isExpelled;
    }

    @Override
    public void setIsExpelled(boolean isExpelled) {
        this.isExpelled = isExpelled;
    }

    // https://www.geeksforgeeks.org/overriding-equals-method-in-java/
    @Override
    public boolean equals(Object o) { 
  
        // If the object is compared with itself then return true   
        if (o == this) { 
            return true; 
        } 
  
        /* Check if o is an instance of Complex or not 
          "null instanceof [type]" also returns false */
        if (!(o instanceof Player)) { 
            return false; 
        } 
          
        // typecast o to Complex so that we can compare data members  
        Player c = (Player) o; 
          
        // Compare the data members and return accordingly  
        return name.equals(c.getName());
    } 


    @Override
    public char toChar() {
        return 'P';
    }

    @Override
    public String update(Point pos, PlayerState state){
        this.pos = pos;
        return state.print();   
    }

    @Override
    public Move userMove(Scanner scanner) {

        System.out.println("Current Position: " + toRowCol(pos).toString());
        System.out.println("Would you like to move your players position? (please enter Y or N)");
        String input = scanner.nextLine();

        while (!input.equalsIgnoreCase("N") && !input.equalsIgnoreCase("Y")) {
            System.out.println("not a valid response."); 
            System.out.println("Would you like to move your players position? (please enter Y or N)");
            input = scanner.nextLine(); 
        }

        //Stays put in current position -> return current position 
        if (input.equalsIgnoreCase("N")) {
            return new Move(pos);
        }

        System.out.println("Enter the row you would like to move to "); 
        int row = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the column you would like to move to "); 
        int col = scanner.nextInt();
        scanner.nextLine();

        Point newPos = new Point(col, row);


        return new Move(newPos); 
    }

    @Override
    public Move userMoveOnServer(DataInputStream dis, DataOutputStream dos, String state, String name) throws IOException {
        
        String s = state + "\n" + "Current Position: " + toRowCol(pos).toString();
        s += "\n" + "Would you like to move your player, " + name + "'s position? (please enter Y or N)";
        dos.writeUTF(s);
        String input = dis.readUTF();
        while (!input.equalsIgnoreCase("N") && !input.equalsIgnoreCase("Y")) {
            dos.writeUTF("not a valid response." + "\n" + "Would you like to move your player, " + name + "'s position? (please enter Y or N)");
            input = dis.readUTF(); 
        }

        //Stays put in current position -> return current position 
        if (input.equalsIgnoreCase("N")) {
           // return new Move(null);
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