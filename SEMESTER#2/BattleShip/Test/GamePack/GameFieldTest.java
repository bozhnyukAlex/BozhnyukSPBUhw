package GamePack;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

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
        int sumBefore = checkAround(0,0);
        field.setBusyAroundCell(0,0, 1);
        int sumAfter = checkAround(0,0);
        assertTrue(sumBefore < sumAfter);
    }

    public int checkAround(int i, int j) {
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
    public void hasFiredShipAroundTestTest() {

    }
}