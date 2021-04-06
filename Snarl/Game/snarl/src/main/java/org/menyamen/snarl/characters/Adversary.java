package org.menyamen.snarl.characters;

import java.awt.Point;
import java.util.List;

import org.menyamen.snarl.layout.Level;

public abstract class Adversary {

    private Point pos;
    private String name;
    private Level level;
    private List<Player> playerList;

    protected Adversary(String name) {
        this.name = name;
    }

    protected Adversary(Point pos) {
        this.pos = pos;
    }

    protected Adversary(Point pos, String name) {
        this.pos = pos;
        this.name = name;
    }

    /**
     * Get position as Point of Adversary.
     * @return Point for Adversary's position.
     */
    public Point getPos() {
        return this.pos;
    }

    /**
     * Set position as Point for Adversary.
     * @param pos Point to use as position for Adversary.
     */
    public void setPos(Point pos) {
        this.pos = pos;
    }

    /**
     * Get name as String of Adversary.
     * @return String of Adversary.
     */
    public String getName() {
        return name;
    }

    /**
     * Set name as String for Adversary.
     * @param pos String to use as name for Adversary.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return type as String for Adversary.
     * @return String of Adversary.
     */
    public abstract String getType();

    /**
     * Get Char representing the Adversary for ASCII Representation.
     * 
     * @return Char to represent the Adversary.
     */
    public abstract char toChar();

     /**
     * Receive an update containing the Level.
     * @param level current level.
     */
    public void levelUpdate(Level level) {
        this.level = level;
    }



}