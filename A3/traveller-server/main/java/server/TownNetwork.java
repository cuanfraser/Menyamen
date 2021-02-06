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

    //re-places a character already at a town (throws exception if town is not in the network) or places a new character
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

    //Determines if there is an empty path from the current town to the destination town
    public boolean canMove(String characterName, String destinationTownName) throws Exception {
        Town destinationTown = allTowns.stream().filter((s) -> s.townName.equals(destinationTownName)).findFirst().get();
        if(destinationTown == null) {
            throw new IllegalArgumentException("This town does not exist");
        }

        Character character = allCharacters.stream().filter((c) -> c.characterName.equals(characterName)).findFirst().get();
        Town currentTown = allTowns.stream().filter((s) -> s.townName.equals(character.characterName)).findFirst().get();

        return this.areEmptyNeighboringTowns(currentTown, destinationTown);

    }

    //Helper function that finds towns that have no characters placed on them
    public List<Town> findEmptyAdjacentTowns(Town destinationTown){
        List<Town> validTowns = new ArrayList<Town>();
        for(String townName : destinationTown.townsAdjacent){
            Town validTown = allTowns.stream().filter((s) -> s.townName.equals(townName) && s.isEmpty()).findFirst().get();
            if(validTown != null)
                validTowns.add(validTown);
        }
        return validTowns;
    }

    //Helper function that recursively finds if two paths overlap
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