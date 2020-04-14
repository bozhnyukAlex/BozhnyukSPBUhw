package GamePack;

import java.util.ArrayList;
import java.util.Arrays;

public class Ship {
    private int length;
    private ArrayList<Cell> decks;
    private Direction direction;

    public Ship(int length) {
        this.length = length;
        decks = new ArrayList<>();

    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
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

    public boolean hasDeckWithThisCoordinates(int di, int dj) {
        for (Cell deck : decks) {
            if (deck.getX() == dj * Cell.SIZE && deck.getY() == di * Cell.SIZE) {
                return true;
            }
        }
        return false;
    }
}
