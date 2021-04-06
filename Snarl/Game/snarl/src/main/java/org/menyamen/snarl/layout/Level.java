package org.menyamen.snarl.layout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.menyamen.snarl.characters.Adversary;
import org.menyamen.snarl.characters.Player;
import org.menyamen.snarl.constraints.CharacterEnum;
import org.menyamen.snarl.gameobjects.GameObject;
import org.menyamen.snarl.tiles.Door;
import org.menyamen.snarl.tiles.OpenTile;
import org.menyamen.snarl.tiles.Tile;
import org.menyamen.snarl.tiles.Wall;

import java.awt.Point;

public class Level {
    private List<Room> rooms;
    private List<Hallway> hallways;
    private HashMap<Point, Tile> map;
    private int sizeX;
    private int sizeY;
    private boolean exitLocked = true;

    public Level(List<Tile> tiles){
        this.map = new HashMap<Point, Tile>();
        int largestX = 0;
        int largestY = 0;
        for (Tile singleTile : tiles) {
            Point currentPoint = singleTile.getPos();
            largestX = currentPoint.x > largestX ? currentPoint.x : largestX;
            largestY = currentPoint.y > largestY ? currentPoint.y : largestY;
            map.put(currentPoint, singleTile);
        }
        sizeX = largestX + 1;
        sizeY = largestY + 1;
    }

    /**
     * Construct Level from single Room.
     * 
     * @param rooms    Rooms to add to Level.
     * @param hallways Hallways to add to Level.
     */
    public Level(Room room) {
        List<Room> temp = new ArrayList<Room>();
        temp.add(room);
        this.rooms = temp;
        this.hallways = new ArrayList<Hallway>();
        this.map = new HashMap<Point, Tile>();
        this.generate();
    }

    /**
     * Construct Level from Rooms, Hallways.
     * 
     * @param rooms    Rooms to add to Level.
     * @param hallways Hallways to add to Level.
     */
    public Level(List<Room> rooms, List<Hallway> hallways) {
        this.rooms = rooms;
        this.hallways = hallways;
        this.map = new HashMap<Point, Tile>();
        this.generate();
    }

    // Level Creation

    /**
     * Generates map for Level from List of Rooms and List of Hallways.
     */
    public void generate() {
        int largestX = 0;
        int largestY = 0;

        // Add Rooms
        for (Room singleRoom : rooms) {
            if (validRoomPlacement(singleRoom)) {
                List<Tile> tiles = singleRoom.getTiles();
                for (Tile singleTile : tiles) {
                    Point currentPoint = singleTile.getPos();
                    largestX = currentPoint.x > largestX ? currentPoint.x : largestX;
                    largestY = currentPoint.y > largestY ? currentPoint.y : largestY;
                    map.put(currentPoint, singleTile);
                }
            }

        }

        // Add Hallways
        for (Hallway singleHallway : hallways) {
            if (!validHallwayPlacement(singleHallway)) {
                throw new IllegalStateException(
                        "Hallway illegal placement. Start: " + singleHallway.getStart().toString());
            } else {
                List<Tile> tiles = singleHallway.getTiles();
                for (Tile singleTile : tiles) {
                    Point currentPoint = singleTile.getPos();
                    largestX = currentPoint.x > largestX ? currentPoint.x : largestX;
                    largestY = currentPoint.y > largestY ? currentPoint.y : largestY;
                    map.put(currentPoint, singleTile);
                }
            }
        }

        sizeX = largestX + 1;
        sizeY = largestY + 1;
    }

    /**
     * Checks if Room has a valid placement in current Level.
     * 
     * @param room Room to test.
     * @return True if valid, false if invalid.
     */
    public boolean validRoomPlacement(Room room) {
        int horizontalSize = room.getHorizontalSize();
        int verticalSize = room.getVerticalSize();
        Point origin = room.getOrigin();

        for (int i = origin.y; i < origin.y + verticalSize; i++) {
            for (int j = origin.x; j < origin.x + horizontalSize; j++) {
                Point currentPos = new Point(j, i);
                if (map.containsKey(currentPos)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if Hallway has a valid placement in current Level.
     * 
     * @param hallway Hallway to test.
     * @return True if valid, false if invalid.
     */
    public boolean validHallwayPlacement(Hallway hallway) {
        List<Tile> tilesNeeded = hallway.getTiles();

        if (!(map.get(hallway.getStart()) instanceof Door)) {
            return false;
        }

        if (!(map.get(hallway.getEnd()) instanceof Door)) {
            System.out.println(map.get(hallway.getEnd()).toChar());

            return false;
        }

        for (Tile currentTile : tilesNeeded) {
            Point currentPos = currentTile.getPos();
            if (map.containsKey(currentPos)) {
                if (map.get(currentPos) instanceof Wall && currentTile instanceof Wall) {
                    continue;
                }
                if (currentPos.equals(hallway.getStart())) {
                    continue;
                }
                if (currentPos.equals(hallway.getEnd())) {
                    continue;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Is given Point in map next to a Tile of the same Class as given tile (above,
     * below, left or right of given Point).
     * 
     * @param point Point to check around on map.
     * @param tile  Tile of Class type to check for (e.g. Wall, OpenTile, Door).
     * @return True if specified type is found, False if not.
     */
    private boolean nextTo(Point point, Tile tile) {
        Point above = new Point(point);
        above.translate(0, -1);
        Point below = new Point(point);
        below.translate(0, 1);
        Point left = new Point(point);
        left.translate(-1, 0);
        Point right = new Point(point);
        right.translate(1, 0);

        if (map.containsKey(above)) {
            Tile ta = map.get(above);
            if (ta.getClass() == tile.getClass()) {
                return true;
            }
        }
        if (map.containsKey(below)) {
            Tile tb = map.get(below);
            if (tb.getClass() == tile.getClass()) {
                return true;
            }
        }
        if (map.containsKey(left)) {
            Tile tl = map.get(left);
            if (tl.getClass() == tile.getClass()) {
                return true;
            }
        }
        if (map.containsKey(right)) {
            Tile tr = map.get(right);
            if (tr.getClass() == tile.getClass()) {
                return true;
            }
        }
        return false;

    }

    /**
     * Add given GameObject at specified Point position.
     * 
     * @param object   GameObject to add.
     * @param position Point where to add GameObject in Level.
     * @return True if successful, False otherwise.
     */
    public boolean addObject(GameObject object, Point position) {
        if (map.containsKey(position)) {
            if (map.get(position) instanceof OpenTile) {
                return map.get(position).setGameObject(object);
            }
        }
        return false;
    }

    /**
     * Utilty function to add tile to map.
     * 
     * @param tile Tile to add.
     */
    protected void addTile(Tile tile) {
        Point pos = tile.getPos();
        map.put(pos, tile);
    }

    public void addPlayers(List<Player> players) {
        for (Player player : players) {
            Point pos = player.getPos();
            if (isOccupied(pos)) {
                throw new IllegalArgumentException("Players have same position");
            }
            map.get(pos).setPlayer(player);
        }
    }

    public void addAdversaries(List<Adversary> adversaries) {
        for (Adversary adversary : adversaries) {
            Point pos = adversary.getPos();
            map.get(pos).setAdversary(adversary);
        }
    }

    // Validation

    /**
     * Returns List<Point> of Traversable Points within 1 or 2 Cardinal move.
     * 
     * @param point Point to check from.
     * @param moves How many cardinal moves.
     * @return List<Point> of Traversable Points.
     */
    public List<Point> cardinalMove(Point point, int moves) {

        List<Point> output = new ArrayList<Point>();

        Point above = new Point(point);
        above.translate(0, -1);
        Point below = new Point(point);
        below.translate(0, 1);
        Point left = new Point(point);
        left.translate(-1, 0);
        Point right = new Point(point);
        right.translate(1, 0);
        Point twoAbove = new Point(point);
        twoAbove.translate(0, -2);
        Point twoBelow = new Point(point);
        twoBelow.translate(0, 2);
        Point twoLeft = new Point(point);
        twoLeft.translate(-2, 0);
        Point twoRight = new Point(point);
        twoRight.translate(2, 0);
        Point aboveRight = new Point(point);
        aboveRight.translate(1, -1);
        Point aboveLeft = new Point(point);
        aboveLeft.translate(-1, -1);
        Point belowRight = new Point(point);
        belowRight.translate(1, 1);
        Point belowLeft = new Point(point);
        belowLeft.translate(-1, 1);

        List<Point> testArray = new ArrayList<Point>();
        if (moves == 1) {
            testArray.add(above);
            testArray.add(left);
            testArray.add(right);
            testArray.add(below);
        } else if (moves == 2) {
            testArray = new ArrayList<Point>(Arrays.asList(twoAbove, aboveLeft, above, aboveRight, twoLeft, left, right,
                    twoRight, below, belowRight, belowLeft, twoBelow));
        } else {
            throw new IllegalArgumentException("1 or 2 Cardinal Moves only");
        }

        for (Point curPoint : testArray) {
            Tile curTile = map.get(curPoint);
            if (!(curTile instanceof Wall)) {
                output.add(curPoint);
            }
        }

        return output;
    }

    /**
     * Return Room containing given Point.
     * 
     * @param point Point to get Room for.
     * @return Room containing Point.
     */
    protected Room getRoomForPoint(Point point) {
        Tile tile = map.get(point);
        if (tile == null) {
            return null;
        }
        for (Room room : rooms) {
            if (room.inRoom(point)) {
                return room;
            }
        }
        return null;
    }

    /**
     * Returns List of origins of Rooms that are immediatly reachable from given
     * Room using Hallways.
     * 
     * @param room Room to check from.
     * @return List of Origins of Rooms.
     */
    public List<Point> reachableFromRoom(Room room) {
        List<Point> output = new ArrayList<Point>();

        for (Hallway hallway : hallways) {
            Point start = hallway.getStart();
            Point end = hallway.getEnd();
            if (room.inRoom(start)) {
                Room roomStart = getRoomForPoint(end);
                if (roomStart != null) {
                    output.add(roomStart.getOrigin());
                }
            } else if (room.inRoom(end)) {
                Room roomEnd = getRoomForPoint(start);
                if (roomEnd != null) {
                    output.add(roomEnd.getOrigin());
                }
            }
        }

        return output;
    }

    /**
     * Returns True if Tile at given Point is OpenTile or Door.
     * 
     * @param point Point to test.
     * @return True if OpenTile or Door, False otherwise.
     */
    public Boolean isTraversable(Point point) {
        Tile tile = map.get(point);
        if (tile == null) {
            return false;
        }
        if (tile instanceof OpenTile || tile instanceof Door) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns List of origins of Rooms that are immediatly reachable from Point's
     * Hallway/Room.
     * 
     * @param point Point to check from.
     * @return List of Origins of Rooms.
     */
    public List<Point> reachableFromPoint(Point point) {
        List<Point> output = new ArrayList<Point>();
        Tile tile = map.get(point);
        if (tile == null) {
            return output;
        }
        for (Room room : rooms) {
            if (room.inRoom(point)) {
                output.addAll(reachableFromRoom(room));
            }
        }
        for (Hallway hallway : hallways) {
            if (hallway.inHallwayAsOpenTile(point)) {
                Point start = hallway.getStart();
                Point end = hallway.getEnd();
                Room startRoom = getRoomForPoint(start);
                Room endRoom = getRoomForPoint(end);
                output.add(startRoom.getOrigin());
                output.add(endRoom.getOrigin());
                break;
            }
        }

        List<Point> outputNoDup = new ArrayList<Point>();
        for (Point currentPoint : output) {
            if (!outputNoDup.contains(currentPoint)) {
                outputNoDup.add(currentPoint);
            }
        }

        return outputNoDup;
    }

    /**
     * Get GameObject at Point.
     * @param point Point to get from.
     * @return GameObject or Null.
     */
    public GameObject getObject(Point point) {
        Tile tile = map.get(point);
        if (tile == null) {
            return null;
        }
        return tile.getGameObject();
    }

    /**
     * Get Tile at Point.
     * @param point Point to get from.
     * @return GameObject or Null.
     */
    public Tile getTile(Point point) {
        Tile tile = map.get(point);
        if (tile == null) {
            return null;
        }
        return tile;
    }


    /**
     * Is Point in Room/Hallway/Void.
     * @param point Point to check.
     * @return String of "room", "hallway", or "void".
     */
    public String whereIsPoint(Point point) {
        Tile tile = map.get(point);
        if (tile == null) {
            return "void";
        }
        for (Room room : rooms) {
            if (room.inRoom(point)) {
                return "room";
            }
        }
        for (Hallway hallway : hallways) {
            if (hallway.inHallwayAsOpenTile(point)) {
                return "hallway";
            }
        }
        return "void";
    }

    /**
     * Print Level as ASCII String.
     * 
     * @return Level as ASCII String.
     */
    public String print() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                Point pos = new Point(j, i);
                if (map.containsKey(pos)) {
                    Tile curTile = map.get(pos);
                    if (curTile.getPlayer() != null) {
                        builder.append(curTile.getPlayer().toChar());
                    } else if (curTile.getAdversary() != null) {
                        builder.append(curTile.getAdversary().toChar());
                    } else if (curTile.getGameObject() != null) {
                        builder.append(curTile.getGameObject().toChar());
                    } else {
                        builder.append(curTile.toChar());
                    }
                } else {
                    builder.append(' ');
                }

                if (j == (sizeX - 1)) {
                    builder.append('\n');
                }
            }
        }
        return builder.toString();
    }

    public Boolean getExitLocked() {
        return exitLocked;
    }

    public void setExitLocked(Boolean exitLocked) {
        this.exitLocked = exitLocked;
    }

    // Playing
    // -------------------------------------------------------------------------

    public boolean isOccupied(Point point) {
        if (map.get(point).getPlayer() != null) {
            return true;
        }
        else {
            return true;
        }
    }

    public CharacterEnum isOccupiedBy(Point point) {
        if (map.get(point).getAdversary() != null) {
            return CharacterEnum.ADVERSARY;
        }
        if (map.get(point).getPlayer() != null) {
            return CharacterEnum.PLAYER;
        }
        else { 
            return CharacterEnum.EMPTY;
        }
    }

    public Player getPlayer(Point point) {
         return map.get(point).getPlayer();
    }

    public Adversary getAdversary(Point point) {
        return map.get(point).getAdversary();
   }

    public List<Tile> viewable(Point point) {

        //2 Cardinal
        Point above = new Point(point);
        above.translate(0, -1);
        Point below = new Point(point);
        below.translate(0, 1);
        Point left = new Point(point);
        left.translate(-1, 0);
        Point right = new Point(point);
        right.translate(1, 0);
        Point twoAbove = new Point(point);
        twoAbove.translate(0, -2);
        Point twoBelow = new Point(point);
        twoBelow.translate(0, 2);
        Point twoLeft = new Point(point);
        twoLeft.translate(-2, 0);
        Point twoRight = new Point(point);
        twoRight.translate(2, 0);
        Point aboveRight = new Point(point);
        aboveRight.translate(1, -1);
        Point aboveLeft = new Point(point);
        aboveLeft.translate(-1, -1);
        Point belowRight = new Point(point);
        belowRight.translate(1, 1);
        Point belowLeft = new Point(point);
        belowLeft.translate(-1, 1);

        // Diagonals
        Point upperLeftDiag = new Point(point);
        upperLeftDiag.translate(-2, -2);
        Point upperRightDiag = new Point(point);
        upperRightDiag.translate(2, -2);
        Point bottomLeftDiag = new Point(point);
        bottomLeftDiag.translate(-2, 2);
        Point bottomRightDiag = new Point(point);
        bottomRightDiag.translate(2, 2);

        List<Point> testArray = new ArrayList<Point>();
        testArray = new ArrayList<Point>(Arrays.asList(point, twoAbove, aboveLeft, above, aboveRight, twoLeft, left, right,
                twoRight, below, belowRight, belowLeft, twoBelow, upperLeftDiag, upperRightDiag, bottomLeftDiag, bottomRightDiag));

        List<Tile> output = new ArrayList<Tile>(25);

        for (Point currentPoint : testArray) {
            Tile currentTile = this.map.get(currentPoint);
            if (currentTile != null) {
                output.add(currentTile);
            }
            
        }

        return output;

    }

    public List<Player> randomPlayersPlacement(List<Player> players) {
        List<Tile> tiles = new ArrayList<Tile>();
        for (int i = 0; i < rooms.size(); i++) {
            tiles.addAll(rooms.get(i).getTiles());
        }
        List<Tile> validTiles = new ArrayList<Tile>();
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).isValidForPlacement()) {
                validTiles.add(tiles.get(i));
            }
        }

        Random random = new Random();
        for (Player player : players) {
            int index = random.nextInt((validTiles.size() - 1) - 0) + 0;
            Point pos = validTiles.get(index).getPos();
            player.setPos(pos);
            map.get(pos).setPlayer(player);
        }

        return players;        

    }

    public List<Adversary> randomAdversariesPlacement(List<Adversary> advers) {
        List<Tile> tiles = new ArrayList<Tile>();
        for (int i = 0; i < rooms.size(); i++) {
            tiles.addAll(rooms.get(i).getTiles());
        }
        List<Tile> validTiles = new ArrayList<Tile>();
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).isValidForPlacement()) {
                validTiles.add(tiles.get(i));
            }
        }

        Random random = new Random();
        for (Adversary ad : advers) {
            int index = random.nextInt((validTiles.size() - 1) - 0) + 0;
            Point pos = validTiles.get(index).getPos();
            ad.setPos(pos);
            map.get(pos).setAdversary(ad);
        }

        return advers;        

    }

}
