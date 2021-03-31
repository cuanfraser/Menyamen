package org.menyamen.snarl.characters;

import org.menyamen.snarl.state.FullState;

public class ObserverImpl implements Observer {
    FullState state;

    public ObserverImpl(){}

    public ObserverImpl(FullState state) {
        this.state = state;
    }

    @Override
    public void update(FullState state) {
        this.state = state;
    }

    @Override
    public String print() {
        return this.state.print();
    }

    
    
}