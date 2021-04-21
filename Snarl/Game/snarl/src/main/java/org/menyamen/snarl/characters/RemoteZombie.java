
package org.menyamen.snarl.characters;

import java.awt.Point;

public class RemoteZombie extends RemoteAdversary {

    protected RemoteZombie(Point pos) {
        super(pos);
        
    }

    protected RemoteZombie(String name) {
        super(name);
        
    }

    @Override
    public String getType() {
        return "zombie";
    }

    @Override
    public char toChar() {
        return 'Z';
    }
  

}
