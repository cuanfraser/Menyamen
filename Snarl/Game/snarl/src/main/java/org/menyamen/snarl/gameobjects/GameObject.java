package org.menyamen.snarl.gameobjects;

public interface GameObject {

    public enum GameObjectType {
        EXIT,
        KEY
    }

    /**
     * Get Char representing the GameObject type for ASCII Representation.
     * @return Char to represent the GameObject Class.
     */
    char toChar();
    
    /**
     * Get String representing the GameObject type for JSON and Debugging.
     * @return String representing the GameObject Class.
     */
    String toString();
    
    /**
     * Get Type of GameObject as GameObject Type.
     * @return GameObjectType for GameObject.
     */
    GameObjectType getType();

}