package traveller;

import java.util.List;


interface ITraveller {
    // Returns the amount of nodes in a network
    // POST: Integer value of the amount of nodes, or 0 if none
    public Integer Size(List<Node> nodes);


    // Returns a boolean statement whether or not a node has a character or not
    // PRE: a given node must be present
    // POST: whether or not the node has a character on it
    public boolean isEmpty(Node node);


    // Places roads to construct a network of edges
    // POST: roads that will form the town
    public void construct(List<Road> roads);


    // Places character at a node
    // PRE: that node is empty
    // POST: node is not empty 
    public void placeChar(TownCharacter ch, Node node);

    
    // Returns a boolean value whether or not a route is found for a character.
    // PRE: a given character, and the destination node 
    // POST: boolean value whether or not the path is found
    public boolean route(TownCharacter ch, Node node);
}