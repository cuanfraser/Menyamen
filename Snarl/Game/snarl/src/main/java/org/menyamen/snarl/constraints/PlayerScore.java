package org.menyamen.snarl.constraints;

//can be assigned to a player or adversary 
public class PlayerScore {
    private String name;
    private int exits;
    private int ejects;
    private int keys;

    public PlayerScore(String name) {
      this.name = name;
      this.exits = 0;
      this.ejects = 0;
      this.keys = 0;
    }

    public String getName() {
        return this.name;
    }

    public int getExits() {
        return this.exits;
    }

    public void addExits(int exit) {
        this.exits += exit;
    }

    public int getEjects() {
        return this.ejects;
    }

    public void addEjects(int eject) {
        this.ejects += eject;
    }

    public int getkeys() {
        return this.keys;
    }

    public void addkeys(int key) {
        this.keys += key;
    }

    //print the leaderboard 
    public String printPlayerScore(){
        return String.format("Name: %s\n Ejects: %s\n Exits: %s\n Keys: %s", this.name, this.ejects, this.exits, this.keys);

    }
}
