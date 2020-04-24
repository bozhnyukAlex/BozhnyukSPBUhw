package GamePack;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class LogicTest {
    private Logic logic;
    private GameField field;
    @Before
    public void setUp() throws Exception {
        logic = new Logic(GameMode.ONE_PLAYER);
        field = new GameField();
        field.initAndDraw();
        logic.initAI(field, IntelligenceLevel.MEDIUM);
    }

    @Test
    public void autoShipGenerateTest() {
        ArrayList<Ship> resTest = logic.autoShipGenerate(field);
        int cnt4 = 0, cnt3 = 0, cnt2 = 0, cnt1 = 0;
        int[][] cnt = new int[10][10];
        for (Ship ship : resTest) {
            switch (ship.getLength()){
                case 4: {
                    cnt4++;
                    break;
                }
                case 3: {
                    cnt3++;
                    break;
                }
                case 2: {
                    cnt2++;
                    break;
                }
                case 1: {
                    cnt1++;
                    break;
                }
            }
            for (Cell deck : ship.getDecks()) {
                cnt[deck.getI()][deck.getJ()]++;
                assertTrue(cnt[deck.getI()][deck.getJ()] <= 1);
            }
        }
        assertEquals(cnt1, 4);
        assertEquals(cnt2, 3);
        assertEquals(cnt3, 2);
        assertEquals(cnt4, 1);
    }

    @Test
    public void canSetTest() {
        assertFalse(logic.canSet(0, 0, Direction.DIR_UP, 4, field));
        assertFalse(logic.canSet(0, 0, Direction.DIR_LEFT, 4, field));
        assertFalse(logic.canSet(9, 9, Direction.DIR_RIGHT, 4, field));
        assertFalse(logic.canSet(9, 9, Direction.DIR_DOWN, 4, field));
        assertTrue(logic.canSet(0,0, Direction.DIR_DOWN, 4, field));
        Ship ship = new Ship(new Cell(0,Cell.SIZE), new Cell(Cell.SIZE, Cell.SIZE));
        field.setShip(ship);
        assertFalse(logic.canSet(0,0, Direction.DIR_DOWN, 4, field));
        assertFalse(logic.canSet(1,2,Direction.DIR_DOWN, 1, field));
    }

    @Test
    public void getShipByDeckTest() {
        Ship testShip = new Ship(new Cell(0,0), new Cell(0, Cell.SIZE));
        field.setShip(testShip);
        logic.addPlayerShip(testShip);
        assertEquals(logic.getShipByDeck(field.getCell(0, 0) ,logic.getShips(Logic.PLAYER_SHIPS)), testShip);
    }

}