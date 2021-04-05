package org.menyamen.snarl.characters;

import java.awt.Point;
import java.util.Scanner;

import org.menyamen.snarl.state.PlayerState;

public class PlayerImpl implements Player {
    private String name;
    private Point pos;

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


    public String getName() {
        return name;
    }

    public Point getPos() {
        return this.pos;
    }

    public void setPos(Point pos) {
        this.pos = pos;
    }

    // https://www.geeksforgeeks.org/overriding-equals-method-in-java/
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


    /**
     * Get Char representing the Player for ASCII Representation.
     * 
     * @return Char to represent the Player.
     */
    public char toChar() {
        return 'P';
    }

     /**
     * Receive an update containing the player avatar’s position in the level and the current state of
     * their immediate surroundings. 
     * @param pos current position of player
     * @param state current state of immediate surroundings
     */
   
    public String update(Point pos, PlayerState state){
        this.pos = pos;
        return state.print();   
    }

   
    public Point userMove(Scanner scanner) {

        System.out.println("Would you like to move your players position? (please enter Y or N)");
        String input = scanner.nextLine();

        while (!input.equalsIgnoreCase("N") && !input.equalsIgnoreCase("Y")) {
            System.out.println("not a valid response."); 
            System.out.println("Would you like to move your players position? (please enter Y or N)");
            input = scanner.nextLine(); 
        }

        //Stays put in current position -> return current position 
        if (input.equalsIgnoreCase("N")) {
            return this.getPos();
        }

        System.out.println("Enter the row you would like to move to "); 
        int row = scanner.nextInt();
        System.out.println("Enter the column you would like to move to "); 
        int col = scanner.nextInt();
        Point newPos = new Point(col, row);


        return newPos; 
    }
}