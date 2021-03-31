package org.menyamen.snarl.state;

import java.util.List;

import org.menyamen.snarl.characters.Adversary;
import org.menyamen.snarl.characters.Player;
import org.menyamen.snarl.layout.Level;
import org.menyamen.snarl.tiles.Tile;

public class PlayerState {
    private Player player;
    private List<Tile> tiles;
    private List<Player> otherPlayers;
    private List<Adversary> adversaries;
    private Level level;

    public PlayerState(Player player, List<Tile> tiles, List<Player> players, List<Adversary> adversaries) {
        this.setPlayer(player);
        this.setTiles(tiles);
        this.setPlayers(players);
        this.setAdversaries(adversaries);

        this.level = new Level(tiles);
    }

    public String print() {
        return this.level.print();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Adversary> getAdversaries() {
        return adversaries;
    }

    public void setAdversaries(List<Adversary> adversaries) {
        this.adversaries = adversaries;
    }

    public List<Player> getPlayers() {
        return otherPlayers;
    }

    public void setPlayers(List<Player> players) {
        this.otherPlayers = players;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }
    
    
}