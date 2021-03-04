package org.menyamen.snarl;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.menyamen.snarl.layout.Hallway;
import org.menyamen.snarl.layout.Level;
import org.menyamen.snarl.layout.Room;
import org.menyamen.snarl.gameobjects.ExitPortal;
import org.menyamen.snarl.gameobjects.GameObject;
import org.menyamen.snarl.gameobjects.Key;

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
        room1.addDoor(9, 2);
        Room room2 = new Room(new Point(15, 0), 10, 7);
        room2.addDoor(15, 2);

        List<Room> testRooms= new ArrayList<Room>();
        testRooms.add(room1);
        testRooms.add(room2);

        Hallway hallway1 = new Hallway(new Point(9, 2), new Point(15, 2));

        List<Hallway> testHallways = new ArrayList<Hallway>();
        testHallways.add(hallway1);

        Level testLevel = new Level(testRooms, testHallways);

        GameObject key = new Key();
        GameObject portal = new ExitPortal();

        testLevel.addObject(key, new Point(4, 7));
        testLevel.addObject(portal, new Point(19, 2));


        System.out.println("Example 1:");
        String printed = testLevel.print();

        System.out.println(printed);

        // Example 2 testing

        Room ex2Room1 = new Room(new Point(0, 0), 5, 5);
        ex2Room1.addDoor(4, 2);
        Room ex2Room2 = new Room(new Point(20, 10), 5, 7);
        ex2Room2.addDoor(20, 12);

        List<Room> ex2TestRooms= new ArrayList<Room>();
        ex2TestRooms.add(ex2Room1);
        ex2TestRooms.add(ex2Room2);

        List<Point> waypoints = new ArrayList<Point>();
        waypoints.add(new Point(10, 2));
        waypoints.add(new Point(10, 12));

        Hallway ex2Hallway1 = new Hallway(new Point(4, 2), new Point(20, 12), waypoints);

        List<Hallway> ex2TestHallways = new ArrayList<Hallway>();
        ex2TestHallways.add(ex2Hallway1);

        Level ex2TestLevel = new Level(ex2TestRooms, ex2TestHallways);

        GameObject ex2Key = new Key();
        GameObject ex2Portal = new ExitPortal();

        ex2TestLevel.addObject(ex2Key, new Point(2, 1));
        ex2TestLevel.addObject(ex2Portal, new Point(22, 13));


        String ex2Printed = ex2TestLevel.print();

        System.out.println("Example 2:");
        System.out.println(ex2Printed);

         //Example 3

//         Room ex3Room1 = new Room(new Point(0, 0), 10, 3);
//         ex3Room1.addDoor(9,2);
//         Room ex3Room2 = new Room(new Point(15, 15), 5, 5);
//         ex3Room1.addDoor(15,17);
//
//         List<Room> ex3TestRooms= new ArrayList<Room>();
//         ex3TestRooms.add(ex3Room1);
//         ex3TestRooms.add(ex3Room2);
//
//         List<Point> waypoints2 = new ArrayList<Point>();
//         waypoints2.add(new Point(5, 17));
//
//         Hallway ex3Hallway1 = new Hallway(new Point(5,3), new Point(14,17), waypoints2);
//
//         List<Hallway> ex3TestHallways = new ArrayList<Hallway>();
//         ex3TestHallways.add(ex3Hallway1);
//
//         Level ex3TestLevel = new Level(ex3TestRooms, ex3TestHallways);
//
//         GameObject ex3Key = new Key();
//         GameObject ex3Portal = new ExitPortal();
//
//         ex3TestLevel.addObject(ex3Key, new Point(1, 1));
//         ex3TestLevel.addObject(ex3Portal, new Point(16, 16));
//
//
//         String ex3Printed = ex3TestLevel.print();
//
//         System.out.println("Example 3:");
//         System.out.println(ex3Printed);
    }
}
