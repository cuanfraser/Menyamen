package org.menyamen.snarl;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.menyamen.snarl.layout.Hallway;
import org.menyamen.snarl.layout.Level;
import org.menyamen.snarl.layout.Room;

/**
 * Main Class
 */
public final class App {
    private App() {
    }

    /**
     * Program entry point
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {

        Room room1 = new Room(new Point(0, 0));
        Room room2 = new Room(new Point(15, 0));

        List<Room> testRooms= new ArrayList<Room>();
        testRooms.add(room1);
        testRooms.add(room2);

        Hallway hallway1 = new Hallway(new Point(0, 15), new Point(10, 15));

        List<Hallway> testHallways = new ArrayList<Hallway>();
        testHallways.add(hallway1);

        Level testLevel = new Level(testRooms, testHallways, 40, 20);

        String printed = testLevel.print();

        System.out.println(printed);
    }
}
