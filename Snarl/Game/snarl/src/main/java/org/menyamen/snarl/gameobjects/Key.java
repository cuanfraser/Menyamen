package org.menyamen.snarl.gameobjects;

public class Key implements GameObject {

    public Key(){}

    @Override
    public char toChar() {
        return 'K';
    }

    @Override
    public String toString() {
        return "key";
    }
}