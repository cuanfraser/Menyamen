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

        ex2TestLevel.addObject(ex2Key, new Point(2, 1));
        ex2TestLevel.addObject(ex2Portal, new Point(22, 13));


        String ex2Printed = ex2TestLevel.print();

        System.out.println("Example 2:");
        System.out.println(ex2Printed);

        // Example 3

        Room ex3Room1 = new Room(new Point(0, 0), 10, 3);
        Room ex3Room2 = new Room(new Point(15, 15), 5, 5);

        List<Room> ex3TestRooms= new ArrayList<Room>();
        ex3TestRooms.add(ex3Room1);
        ex3TestRooms.add(ex3Room2);

        List<Point> waypoints2 = new ArrayList<Point>();
        waypoints2.add(new Point(5, 3));
        waypoints2.add(new Point(5, 17));
        waypoints2.add(new Point(14, 17));

        Hallway ex3Hallway1 = new Hallway(waypoints2);

        List<Hallway> ex3TestHallways = new ArrayList<Hallway>();
        ex3TestHallways.add(ex3Hallway1);

        Level ex3TestLevel = new Level(ex3TestRooms, ex3TestHallways, 40, 30);

        GameObject ex3Key = new Key();
        GameObject ex3Portal = new ExitPortal();

        ex3TestLevel.addObject(ex3Key, new Point(1, 1));
        ex3TestLevel.addObject(ex3Portal, new Point(16, 16));


        String ex3Printed = ex3TestLevel.print();

        System.out.println("Example 3:");
        System.out.println(ex3Printed);

        // Example 4

        Room ex4Room1 = new Room(new Point(0, 0), 5, 20);
        Room ex4Room2 = new Room(new Point(10, 0), 5, 5);

        List<Room> ex4TestRooms= new ArrayList<Room>();
        ex4TestRooms.add(ex4Room1);
        ex4TestRooms.add(ex4Room2);

        List<Point> waypoints3 = new ArrayList<Point>();
        waypoints3.add(new Point(5, 16));
        waypoints3.add(new Point(11, 16));
        waypoints3.add(new Point(11, 5));

        Hallway ex4Hallway1 = new Hallway(waypoints3);

        List<Hallway> ex4TestHallways = new ArrayList<Hallway>();
        ex4TestHallways.add(ex4Hallway1);

        Level ex4TestLevel = new Level(ex4TestRooms, ex4TestHallways, 30, 30);

        GameObject ex4Key = new Key();
        GameObject ex4Portal = new ExitPortal();

        ex4TestLevel.addObject(ex4Key, new Point(11, 1));
        ex4TestLevel.addObject(ex4Portal, new Point(1, 1));


        String ex4Printed = ex4TestLevel.print();

        System.out.println("Example 4:");
        System.out.println(ex4Printed);
    }
}
