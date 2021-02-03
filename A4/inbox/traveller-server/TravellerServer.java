package traveller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TravellerServer implements ITraveller {
    List<Node> seenTowns;
    Map<TownCharacter, Node> characterLocation;
    Map<Node, List<Node>> network;

    public TravellerServer() {
        this.seenTowns = new ArrayList<Node>();
        this.characterLocation = new HashMap<TownCharacter, Node>();
        this.network = new HashMap<Node, List<Node>>();
    }

    @Override
    public Integer Size(List<Node> nodes) {
        return nodes.size();
    }

    @Override
    public boolean isEmpty(Node node) {
        return node.ch == null;
    }

    @Override
    public void construct(List<Road> roads) {
        /**
         * Take the list of roads and use a hash map to construct 
         * the network of towns and roads as a simple graph
         * 
         * This gives us an example network like this:
         * 
         * *FROM*      *TO*
         * 
         * Boston:     [New York, Chicago]
         * New York:   [Boston, Chicago]
         * Atlanta:    [Chicago] 
         * Chicago:    [Boston, Atlanta, New York]
         * 
         **/

        for (Road road : roads) {
            this.addToNetwork(road.to, road.from);
            this.addToNetwork(road.from, road.to);
        }
    }

    @Override
    public void placeChar(TownCharacter ch, Node node) {
        /**
         * The strategy of placing a character in a network has two
         * main cases, given a network of nodes as follows:
         * 
         * Boston:     [New York, Chicago]
         * New York:   [Boston, Chicago]
         * Atlanta:    [Chicago] 
         * Chicago:    [Boston, Atlanta, New York]
         * 
         * Placing a character in Atlanta involves updating the 
         * hash map [Atlanta] key to contain the character, as well as
         * cycling through all adjacency lists of other cities and updating the 
         * [Atlanta] node with the character as well.
         * 
         **/

        // Ensure that the node is empty first
        if (!this.isEmpty(node)) {
            return;
        } 

        Set<Node> townKeys = new HashSet<Node>(this.network.keySet());

        for (Node town : townKeys) {
            // get the adjacent towns
            List<Node> adjacentTowns = this.network.get(town);

            if (town == node) {
                // delete old node
                this.network.remove(town); 

                // update new node with character
                node.setTownCharacter(ch);
                this.characterLocation.put(ch, node);

                // create new network entry
                this.network.put(node, adjacentTowns);
            }
            else {
                // Prepare a new adjacency list
                List<Node> newAdjacencyList = new ArrayList<Node>();

                // update the node in each adjacency list
                for (Node adjacentTown : adjacentTowns) {

                    // Add the character
                    if (adjacentTown == node) {
                        adjacentTown.setTownCharacter(ch);
                    }
                   
                    // add the town back to the list
                    newAdjacencyList.add(adjacentTown);
                }

                this.network.put(town, newAdjacencyList);
            }
        }
    }

    @Override
    public boolean route(TownCharacter ch, Node destTown) {
        // find characters starting town
        // check for a path
        boolean path = uninterruptedPath(this.characterLocation.get(ch), destTown);

        // Reset seen towns
        this.seenTowns = new ArrayList<Node>();

        // Return whethere there exists a path
        return path;
    }

    private void addToNetwork(Node firstTown, Node secondTown) {
        if (network.containsKey(firstTown)) {
            network.get(firstTown).add(secondTown);
        }
        else {
            List<Node> toTowns = new ArrayList<Node>();
            toTowns.add(secondTown);

            network.put(firstTown, toTowns);
        }
    }

    private boolean uninterruptedPath(Node start, Node dest) {
        this.seenTowns.add(start);

        for (Node town : network.get(start)) {
            if (this.seenTowns.contains(town)) {
                continue;
            }

            if (town == dest) {
                // we found our destination town
                // make sure no one is occupying it
                return this.isEmpty(town);
            } else {
                // didnt find the dest town
                // if no character is here, then we can pass through
                if (this.isEmpty(town) && uninterruptedPath(town, dest)) {
                    return true;
                }

            }
        }

        return false;
    }
}