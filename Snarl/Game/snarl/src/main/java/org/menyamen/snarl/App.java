package org.menyamen.snarl;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.menyamen.snarl.layout.Hallway;
import org.menyamen.snarl.layout.Level;
import org.menyamen.snarl.layout.Room;
import org.menyamen.snarl.objects.ExitPortal;
import org.menyamen.snarl.objects.GameObject;
import org.menyamen.snarl.objects.Key;

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

        // Example 1

        Room room1 = new Room(new Point(0, 0));
        Room room2 = new Room(new Point(15, 0), 10, 7);

        List<Room> testRooms= new ArrayList<Room>();
        testRooms.add(room1);
        testRooms.add(room2);

        Hallway hallway1 = new Hallway(new Point(10, 2), new Point(14, 2));

        List<Hallway> testHallways = new ArrayList<Hallway>();
        testHallways.add(hallway1);

        Level testLevel = new Level(testRooms, testHallways, 40, 11);

        GameObject key = new Key();
        GameObject portal = new ExitPortal();

        testLevel.addObject(key, new Point(4, 7));
        testLevel.addObject(portal, new Point(19, 2));
        

        System.out.println("Example 1:");
        String printed = testLevel.print();

        System.out.println(printed);

        // Example 2

        Room ex2Room1 = new Room(new Point(0, 0), 5, 5);
        Room ex2Room2 = new Room(new Point(20, 10), 5, 7);

        List<Room> ex2TestRooms= new ArrayList<Room>();
        ex2TestRooms.add(ex2Room1);
        ex2TestRooms.add(ex2Room2);

        List<Point> waypoints = new ArrayList<Point>();
        waypoints.add(new Point(5, 2));
        waypoints.add(new Point(10, 2));
        waypoints.add(new Point(10, 12));
        waypoints.add(new Point(19, 12));

        Hallway ex2Hallway1 = new Hallway(waypoints);

        List<Hallway> ex2TestHallways = new ArrayList<Hallway>();
        ex2TestHallways.add(ex2Hallway1);

        Level ex2TestLevel = new Level(ex2TestRooms, ex2TestHallways, 40, 20);

        GameObject ex2Key = new Key();
        GameObject ex2Portal = new ExitPortal();

        ex2TestLevel.addObject(ex2Key, new Point(4, 7));
        ex2TestLevel.addObject(ex2Portal, new Point(19, 2));
        

        String ex2Printed = ex2TestLevel.print();

        System.out.println("Example 2:");
        System.out.println(ex2Printed);
    }
}
