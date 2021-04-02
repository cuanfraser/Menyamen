package org.menyamen.snarl.characters;

import java.awt.Point;
import java.util.Scanner;

import org.menyamen.snarl.state.FullState;
import org.menyamen.snarl.util.TestingUtil;

public class PlayerImp implements Player {
    private String name;
    private Point pos;
    int row = 0;
    int col = 0;

    public PlayerImp(String name) {
        this.name = name;
    }

    public PlayerImp(String name, Point pos) {
        this(name);
        this.pos = pos;
    }

    public PlayerImp(String name, int x, int y) {
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
   
    public String update(Point pos, FullState state){
       return state.print();   
    }

   
    public Point userMove(Scanner scanner, Player user) {

        System.out.println("Would you like to move your players position? (please enter Y or N)");
        String input = scanner.nextLine();

        while (!input.equalsIgnoreCase("N") && !input.equalsIgnoreCase("Y")) {
            System.out.println("not a valid response."); 
            System.out.println("Would you like to move your players position? (please enter Y or N)");
            input = scanner.nextLine(); 
        }

        //Stays put in current position -> return current position 
        if (input.equalsIgnoreCase("N")) {
            return user.getPos();
        }

        System.out.println("Enter the row you would like to move to "); 
        row = TestingUtil.rowCol(scanner.nextLine());
        int rowPosition = scanner.nextInt(); 
        System.out.println("Enter the column you would like to move to "); 
        col = TestingUtil.rowCol(scanner.nextLine());
        Point newPos = new Point(row, col);


        return newPos; 
    }
}