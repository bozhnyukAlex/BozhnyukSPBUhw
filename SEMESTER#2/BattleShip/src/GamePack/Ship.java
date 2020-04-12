package GamePack;

import java.util.ArrayList;
import java.util.Arrays;

public class Ship {
    private int length;
    private ArrayList<Cell> decks;

    public Ship(int length) {
        this.length = length;
        decks = new ArrayList<>();

    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void build(Cell ... cells) {
        decks.addAll(Arrays.asList(cells));
    }

    public ArrayList<Cell> getDecks() {
        return decks;
    }
}
