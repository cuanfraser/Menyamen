package traveller;


public class Node {
    String town;
    TownCharacter ch;

    Node(String town) {
        this.town = town;
    }

    public String getTown() {
        return this.town;
    }

    public TownCharacter getTownCharacter() {
        return this.ch;
    }

    public void setTownCharacter(TownCharacter ch) {
        this.ch = ch;
    }
}