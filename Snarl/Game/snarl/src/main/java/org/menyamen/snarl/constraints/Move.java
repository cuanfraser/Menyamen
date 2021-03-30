package org.menyamen.snarl.constraints;

import java.awt.Point;

public class Move {

    Point destination;
    Boolean stayStill;

    public Move(Point destination) {
        if (destination == null) {
            this.stayStill = true;
        }
        else {
            this.stayStill = false;
            this.destination = destination;
        }
    }

    public Point getDestination() {
        return this.destination;
    }

    public Boolean getStayStill() {
        return this.stayStill;
    }

}