package org.menyamen.snarl.characters;

import java.awt.Point;

public class RemoteGhost extends RemoteAdversary {

    protected RemoteGhost(Point pos) {
        super(pos);
        
    }

    protected RemoteGhost(String name) {
        super(name);
        
    }

    @Override
    public String getType() {
        return "ghost";
    }

    @Override
    public char toChar() {
        return 'G';
    }
  

}
