package org.menyamen.snarl.gameobjects;

public class ExitPortal implements GameObject {

    public ExitPortal() {
    }
    
    @Override
    public char toChar() {
        return 'O';
    }

    @Override
    public String toString() {
        return "exit";
    }

    @Override
    public GameObjectType getType() {
        return GameObjectType.EXIT;
    }
}