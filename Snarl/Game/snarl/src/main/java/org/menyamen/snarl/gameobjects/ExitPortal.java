package org.menyamen.snarl.gameobjects;

public class ExitPortal implements GameObject {

    private Boolean locked = true;

    public ExitPortal() {
    }

    public ExitPortal(Boolean locked) {
        this.setLocked(locked);
    }
    
    @Override
    public char toChar() {
        return 'O';
    }

    @Override
    public String toString() {
        return "exit";
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }
}