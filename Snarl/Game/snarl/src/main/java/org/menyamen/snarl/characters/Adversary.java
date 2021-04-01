package org.menyamen.snarl.characters;

import java.awt.Point;

public interface Adversary {

    /**
     * Get position as Point of Adversary.
     * @return Point for Adversary's position.
     */
    public Point getPos();

    /**
     * Set position as Point for Adversary.
     * @param pos Point to use as position for Adversary.
     */
    public void setPos(Point pos);
    
    /**
     * Get name as String of Adversary.
     * @return String of Adversary.
     */
    public String getName();

    /**
     * Set name as String for Adversary.
     * @param pos String to use as name for Adversary.
     */
    public void setName(String name);

    /**
     * Return type as String for Adversary.
     * @return String of Adversary.
     */
    public String getType();

    /**
     * Get Char representing the Adversary for ASCII Representation.
     * 
     * @return Char to represent the Adversary.
     */
    public char toChar();

}