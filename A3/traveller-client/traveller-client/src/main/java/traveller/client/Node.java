package traveller.client;

/**
 * Node represents a town.
 */
public class Node {
    private String town;
    private Character character;

    /**
     * Construct a Node (Town) with no Character with specified name.
     * @param town Specified name of town.
     */
    public Node(String town) {
        this.town = town;
    }

    /**
     * Constructs a Node (Town) with specified name and Character.
     * @param town Specified name of town.
     * @param character Specified Character for Node.
     */
    public Node(String town, Character character) {
        this.town = town;
        this.character = character;
    }

    /**
     * Returns Does Node have a Character?
     * @return True if Node has Character, else False.
     */
    public boolean isEmpty() {
        return character == null;
    }

    /**
     * Place a Character in Node if no Character exists in Node. Else throw IllegalStateException.
     * @param newCharacter New Character to place in Node.
     * @throws IllegalStateException If Character already exists in Node.
     */
    public void placeChar(Character newCharacter) throws IllegalStateException {
        if (character == null) {
            this.character = newCharacter;
        } else {
            throw new IllegalStateException("Node not empty");
        }
    }
}
