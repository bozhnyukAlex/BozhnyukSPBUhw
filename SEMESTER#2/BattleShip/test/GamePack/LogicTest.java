package GamePack;

import org.app.Config;
import org.app.StringConst;
import org.junit.Before;
import org.junit.Test;
import org.game.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.logging.SocketHandler;

import static org.junit.Assert.*;

public class LogicTest {
    private Logic logic;
    private GameField field;
    @Before
    public void setUp() {
        //logic = new Logic(GameMode.ONE_PLAYER);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        logic = context.getBean("logic", Logic.class);
        logic.setContext(context);
        //field = new GameField();
        field = context.getBean("playerField", GameField.class);
        field.initAndDraw();
      //  logic.initAI(field, IntelligenceLevel.MEDIUM);
        logic.initAiWithContainer(field, IntelligenceLevel.MEDIUM);
    }

    @Test
    public void autoShipGenerateTest() {
        ArrayList<Ship> resTest = logic.autoGenerate(field);
        assertTrue(logic.getEnableCounts(1) == 0 && logic.getEnableCounts(2) == 0
                    && logic.getEnableCounts(3) == 0 && logic.getEnableCounts(4) == 0);
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

    @Test
    public void processDeletingFullShipTest() {
        Ship testShip = new Ship(new Cell(0, 3 * Cell.SIZE), new Cell(Cell.SIZE, 3 * Cell.SIZE), new Cell(2 * Cell.SIZE, 3 * Cell.SIZE), new Cell(3 * Cell.SIZE, 3 * Cell.SIZE));
        field.setShip(testShip);

        logic.addPlayerShip(testShip);
        int ret = logic.deleteProcessing(3, 2, field);

        assertEquals(4, ret);
        assertTrue(logic.isPlayerListEmpty());
        assertTrue(field.getCell(3, 0).isEmpty());
        assertTrue(field.getCell(3, 1).isEmpty());
        assertTrue(field.getCell(3, 2).isEmpty());
        assertTrue(field.getCell(3, 3).isEmpty());
        assertEquals(2, logic.getEnableCounts(4));
    }

    @Test
    public void processDeletingFirstClickShipTest() {
        field.getCell(1,1).setCondition(Condition.SHIP_FIRST_CLICK);
        logic.addPlayerShip(new Ship(new Cell(25,25)));
        int ret = logic.deleteProcessing(1,1, field);
        assertTrue(field.getCell(1,1).isEmpty());
        assertEquals(ret, -1);
        assertTrue(logic.isPlayerListEmpty());
    }

    @Test
    public void processSettingShipFirstClick() {
        logic.setTrigger(3, true);
        String ret = logic.processSettingShip(3, 1, field);
        assertEquals(1, logic.fieldClickCount());
        assertTrue(field.getCell(3, 1).isNotShotDeck());
        assertFalse(logic.isPlayerListEmpty());
        assertEquals(ret, StringConst.SET_DIR);
    }

    @Test
    public void processSettingTwoClickShipTest() {
        logic.setTrigger(3, true);
        logic.processSettingShip(3, 1, field);
        String ret = logic.processSettingShip(3, 2, field);
        assertEquals(0, logic.fieldClickCount());
        assertFalse(logic.isPlayerListEmpty());
        assertEquals(ret, StringConst.CHOOSE_SHIP);
        assertTrue(field.getCell(3,1).isNotShotDeck());
        assertTrue(field.getCell(3,2).isNotShotDeck());
        assertTrue(field.getCell(3,3).isNotShotDeck());
        assertFalse(field.getCell(3,4).isNotShotDeck());
    }

    @Test
    public void processSettingOneDeckShipTest() {
        logic.setTrigger(1, true);
        String ret = logic.processSettingShip(3, 2, field);
        assertTrue(field.getCell(3,2).isNotShotDeck());
        assertEquals(0, logic.fieldClickCount());
        assertFalse(logic.isPlayerListEmpty());
        assertEquals(ret, StringConst.CHOOSE_SHIP);
    }

    @Test
    public void processMakingShotToWaterTest() {
        logic.processShot(0,0, field);
        assertTrue(field.getCell(0, 0).isShotWater());
        assertTrue(logic.isPlayerMove());
    }
    @Test
    public void processMakingShotDamageAndKill() {
        logic.setTrigger(2, true);
        logic.processSettingShip(0, 0, field);
        logic.processSettingShip(0, 1, field);
        logic.processShot(0, 0, field);
        assertEquals(10, logic.getPlayerShipsLeft());
        assertTrue(field.getCell(0, 0).isDamagedDeck());
        logic.processShot(0 ,1, field);
        assertEquals(9, logic.getPlayerShipsLeft());
        assertTrue(field.getCell(0, 0).isShipKilledDeck());
        assertTrue(field.getCell(0, 1).isShipKilledDeck());
    }

}