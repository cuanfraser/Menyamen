package server;

import java.util.*;

public class TownNetwork {
    public List<Town> allTowns;
    public List<Character> allCharacters;

    public TownNetwork(Map<String, List<String>> towns, Map<String, String> characters)throws Exception{
        for (Map.Entry<String, List<String>> entry : towns.entrySet()) {
            String townName = entry.getKey();
            List<String> adjacentTownNames = entry.getValue();
            allTowns.add(new Town(townName, adjacentTownNames));
        }
        for (Map.Entry<String, String> entry : characters.entrySet()) {
            String charactername = entry.getKey();
            String townName = entry.getValue();
            this.Place(charactername, townName);

        }
    }

    public void Place(String characterName, String destinationTownName) throws Exception{
        Town destinationTown = allTowns.stream().filter((s) -> s.townName.equals(destinationTownName)).findFirst().get();
        if(destinationTown == null) {
            throw new IllegalArgumentException("This town does not exist");
        }
        boolean addNewCharacter = true;
        for(Town town :allTowns){
            for(Character character: town.townCharacters) {
                if(character.characterName.equals(characterName)){
                    if(!town.townName.equals(destinationTownName)) {
                        character.currentTown = destinationTownName;
                        town.townCharacters.remove(character);
                        destinationTown.townCharacters.add(character);
                    }
                    addNewCharacter = false;
                    break;
                }
            }
            if(addNewCharacter){
                Character newCharacter = new Character(characterName, destinationTownName);
                destinationTown.townCharacters.add(newCharacter);
                this.allCharacters.add(newCharacter);
            }
        }
    }

    public boolean canMove(String characterName, String destinationTownName) throws Exception {
        Town destinationTown = allTowns.stream().filter((s) -> s.townName.equals(destinationTownName)).findFirst().get();
        if(destinationTown == null) {
            throw new IllegalArgumentException("This town does not exist");
        }

        //find all towns between destination and character's town which don't have a character .. there should be some sort of recursion
        //to find a route .. ie find all the towns that are adjacent to the two towns

        Character character = allCharacters.stream().filter((c) -> c.characterName.equals(characterName)).findFirst().get();
        Town currentTown = allTowns.stream().filter((s) -> s.townName.equals(character.characterName)).findFirst().get();

        return this.areEmptyNeighboringTowns(currentTown, destinationTown);

    }

    public List<Town> findEmptyAdjacentTowns(Town destinationTown){
        List<Town> validTowns = new ArrayList<Town>();
        for(String townName : destinationTown.townsAdjacent){
            Town validTown = allTowns.stream().filter((s) -> s.townName.equals(townName) && s.isEmpty()).findFirst().get();
            if(validTown != null)
                validTowns.add(validTown);
        }
        return validTowns;
    }

    public boolean areEmptyNeighboringTowns(Town a, Town b) {
        List<Town> ATowns = this.findEmptyAdjacentTowns(a);
        List<Town> BTowns = this.findEmptyAdjacentTowns(b);
        for (Town i : ATowns) {
            for (Town y : BTowns) {
                if (i.townName == y.townName) {
                    return true;
                }
                this.areEmptyNeighboringTowns(i,y);
            }

        }
        return false;
    }

}