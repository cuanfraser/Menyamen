package org.menyamen.snarl.characters;

import java.awt.Point;

public class Player {
    private String name;
    private Point pos;

    public Player(String name) {
        this.name = name;
    }

    public Player(String name, Point pos) {
        this(name);
        this.pos = pos;
    }

    public Player(String name, int x, int y) {
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
        return name.equals(c.name);
    } 


    /**
     * Get Char representing the Player for ASCII Representation.
     * 
     * @return Char to represent the Player.
     */
    public char toChar() {
        return 'P';
    }
}