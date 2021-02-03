package traveller;

import java.util.ArrayList;
import java.util.List;

public class TestTravellerServer {
    public static void main(String[] args) {
        /** 
         * Testing the server
         **/ 

        // create server
        TravellerServer server = new TravellerServer();

        // create characters
        TownCharacter alec = new TownCharacter("Alec");
        TownCharacter bryan = new TownCharacter("Bryan");

        // create towns
        Node boston = new Node("Boston");
        Node chicago = new Node("Chicago");
        Node newYorkCity = new Node("New York City");
        Node losAngeles = new Node("Los Angeles");
        Node sanFrancisco = new Node("San Francisco");
        Node atlanta = new Node("Atlanta");
        Node honolulu = new Node("Honolulu");

        // create roads
        Road r1 = new Road(boston, newYorkCity);
        Road r2 = new Road(boston, losAngeles);
        Road r3 = new Road(losAngeles, sanFrancisco);
        Road r4 = new Road(sanFrancisco, chicago);
        Road r5 = new Road(sanFrancisco, atlanta);
        Road r6 = new Road(chicago, newYorkCity);
        Road r7 = new Road(atlanta, newYorkCity);
        Road r8 = new Road(newYorkCity, honolulu);

        // add roads to a list
        List<Road> roads = new ArrayList<Road>();

        roads.add(r1);
        roads.add(r2);
        roads.add(r3);
        roads.add(r4);
        roads.add(r5);
        roads.add(r6);
        roads.add(r7);
        roads.add(r8);

        // add road list to town network
        server.construct(roads);

        // add characters to towns
        server.placeChar(alec, boston);
        server.placeChar(bryan, newYorkCity);

        boolean isPath1 = server.route(alec, atlanta);     // expected: true
        boolean isPath2 = server.route(bryan, honolulu);   // expected: true
        boolean isPath3 = server.route(alec, chicago);     // expected: true
        boolean isPath4 = server.route(bryan, losAngeles); // expected: true
        boolean isPath5 = server.route(alec, honolulu);    // expected: false
        boolean isPath6 = server.route(alec, newYorkCity); // expected: false

        System.out.println(isPath1);
        System.out.println(isPath2);
        System.out.println(isPath3);
        System.out.println(isPath4);
        System.out.println(isPath5);
        System.out.println(isPath6);
    }
}