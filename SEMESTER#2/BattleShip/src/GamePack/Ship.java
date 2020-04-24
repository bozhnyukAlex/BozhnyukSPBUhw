package GamePack;

import java.util.ArrayList;

public class Ship {
    private int length;
    private ArrayList<Cell> decks;

    public Ship(int length) {
        this.length = length;
        decks = new ArrayList<>();
    }

    public Ship(Cell ... decks) {
        this.length = decks.length;
        this.decks = new ArrayList<Cell>();
        build(decks);
    }

    public Ship() {}

    public int getLength() {
        return length;
    }

    public void build(Cell ... cells) {
        for (Cell cell : cells) {
            decks.add(cell);
            cell.setDeck(true);
        }
    }

    public void getDamage() {
        length--;
    }

    public ArrayList<Cell> getDecks() {
        return decks;
    }

    public boolean hasDeckWithThisCoordinates(int di, int dj) {
        for (Cell deck : decks) {
            if (deck.getX() == dj * Cell.SIZE && deck.getY() == di * Cell.SIZE) {
                return true;
            }
        }
        return false;
    }

    public boolean isDestroyed() {
        return length == 0;
    }
}
