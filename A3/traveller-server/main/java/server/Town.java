package server;

import java.util.ArrayList;
import java.util.List;

public class Town {
    public String townName;
    public List<Character> townCharacters;
    public List<String> townsAdjacent;

    public Town(String name, List<String> townsAdjacent){
        this.townName = name;
        this.townsAdjacent = townsAdjacent;
        this.townCharacters = new ArrayList<Character>();
    }

    public boolean isEmpty(){
        return this.townCharacters.isEmpty();
    }
}