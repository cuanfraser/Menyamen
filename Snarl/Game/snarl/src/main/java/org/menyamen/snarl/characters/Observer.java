package org.menyamen.snarl.characters;

import org.menyamen.snarl.state.FullState;

public interface Observer {
    
    /**
     * Update State
     */
    public void update(FullState state);


    /**
     * Print State of Game.
     * @return String representation of Level and Actors/Objects in it.
     */
    public String print();
}