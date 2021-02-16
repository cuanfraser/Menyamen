package org.menyamen.snarl.tiles;

import java.awt.Point;

public class Wall implements Tile {
    private Point pos;

    public Wall(int x, int y) {
        this.pos = new Point(x, y);
    }

    @Override
    public char toChar() {
        return '+';
    }

    @Override
    public Point getPos() {
        return pos;
    }

    // Credit: https://www.geeksforgeeks.org/overriding-equals-method-in-java/
    @Override
    public boolean equals(Object o) { 
        // If the object is compared with itself then return true   
        if (o == this) { 
            return true; 
        } 
  
        /* Check if o is an instance of Wall or not 
          "null instanceof [type]" also returns false */
        if (!(o instanceof Wall)) { 
            return false; 
        } 
          
        // typecast o to Wall so that we can compare data members  
        Wall c = (Wall) o; 
          
        // Compare the data members and return accordingly  
        return pos == c.pos ;
    } 
}