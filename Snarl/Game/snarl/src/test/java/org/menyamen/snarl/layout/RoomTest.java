package org.menyamen.snarl.layout;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import java.awt.Point;

/**
 * Unit test for Room Class.
 */
class RoomTest {

    private Room room;

    @BeforeEach
    public void setup() {
        room = new Room(new Point(10, 10));
    }

    /**
     * Rigorous Test.
     */
    @Test
    @DisplayName("Check testRoom() works")
    void testInRoom() {
        assertEquals(true, room.inRoom(new Point(10, 10)));
        assertEquals(true, room.inRoom(new Point(11, 11)));
        assertEquals(true, room.inRoom(new Point(11, 11)));
        assertEquals(true, room.inRoom(new Point(19, 19)));

        assertEquals(false, room.inRoom(new Point(5, 5)));
        assertEquals(false, room.inRoom(new Point(-1, -1)));
        assertEquals(false, room.inRoom(new Point(20, 20)));
    }
}
