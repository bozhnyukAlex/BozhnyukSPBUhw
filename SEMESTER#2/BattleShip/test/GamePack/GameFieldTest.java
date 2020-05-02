package GamePack;

import org.game.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameFieldTest {
    private GameField field;

    @Before
    public void setUp() throws Exception {
        field = new GameField();
        field.initAndDraw();
    }

    @Test
    public void setBusyAroundCellTest() {
        int sumBefore = checkAroundBusy(0,0);
        field.setBusyAroundCell(0,0, 1);
        int sumAfter = checkAroundBusy(0,0);
        assertTrue(sumBefore < sumAfter);
    }

    public int checkAroundBusy(int i, int j) {
        int sum = 0;
        for (int w = -1; w <= 1; w++) {
            for (int v = -1; v <= 1; v++) {
                if (GameField.inRange(i + w, j + v)) {
                    sum += field.getCell(i + w, j + v).getBusyCount();
                }
            }
        }
        return sum;
    }

    public boolean checkAroundDeckAndShot(int i, int j) {
        for (int w = -1; w <= 1; w++) {
            for (int v = -1; v <= 1; v++) {
                if (GameField.inRange(i + w, j + v) && field.getCell(i + w, j + v).isDeck() && field.getCell(i + w, j + v).isShot()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Test
    public void inRangeTest() {
        int ti = -1, tj = 1;
        assertFalse(GameField.inRange(ti, tj));
        ti = 2;
        tj = 5;
        assertTrue(GameField.inRange(ti, tj));
        ti = 2;
        tj = 10;
        assertFalse(GameField.inRange(ti, tj));
        ti = 6;
        tj = -19;
        assertFalse(GameField.inRange(ti, tj));
    }

    @Test
    public void hasFiredShipAroundTest() {
        assertFalse(field.hasFiredShipAround(1, 5));
        field.getCell(2,5).setDeck(true);
        field.getCell(2, 5).setShot(true);
        assertTrue(field.hasFiredShipAround(1, 5));
    }
}