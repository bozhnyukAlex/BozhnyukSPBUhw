package GamePack;

import org.game.Condition;
import org.app.Config;
import org.game.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertTrue;

public class AITest {
    private AI ai;
    private GameField field;
    @Before
    public void setUp() throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        field = new GameField();
       // field = context.getBean("playerField", GameField.class);
        field.initAndDraw();
        ai = new AI(field, IntelligenceLevel.LOW);
      //  ai = context.getBean("enemyAILow", AI.class);
    }

    @Test
    public void makeShotLowTest() {
        Cell shot = testShot();
        assertTrue(checkShot());
    }

    @Test
    public void makeShotHighTest() { //медиум тестировать нет смысла, так как он может только добивать корабли, это тестируется в high
        ai.setILevel(IntelligenceLevel.HIGH);
        Cell shot;
        Ship testShip = new Ship(new Cell(0, 3 * Cell.SIZE), new Cell(Cell.SIZE, 3 * Cell.SIZE), new Cell(2 * Cell.SIZE, 3 * Cell.SIZE), new Cell(3 * Cell.SIZE, 3 * Cell.SIZE));
        field.setShip(testShip);
        for (int i = 0; i < 6; i++) { //тест добития корабля, за 6 выстрелов корабль должен умереть
            shot = ai.makeShot();
            if (shot.isEmpty()) {
                shot.setCondition(Condition.SHOT_WATER);
            }
            if (shot.isNotShotDeck()) {
                testShip.getDamage();
                shot.setCondition(Condition.SHIP_DAMAGED);
            }
        }
        assertTrue(testShip.isDestroyed());
        ai.setShipDead(true);
        field.setWaterAroundDestroyedShip(testShip);
        shot = testShot();
        assertTrue(shot.getI() == 1 && shot.getJ() == 2); //проверяем правильность следующего выстрела
        shot = testShot();
        shot = testShot();
        assertTrue(shot.getI() == 7 && shot.getJ() == 0); //проверяем перескок 1
        for (int i = 0; i < 5; i++) { //переходя диагональ, должен сделать 5 выстрелов, так как там находится корабль
            shot = testShot();
        }
        shot = testShot();
        assertTrue(shot.getI() == 9 && shot.getJ() == 2); //и проверяем еще один перескок и так далее
        for (int i = 0; i < 7; i++) {
            shot = testShot();
        }
        shot = testShot();
        assertTrue(shot.getI() == 9 && shot.getJ() == 6);
        for (int i = 0; i < 3; i++) {
            shot = testShot();
        }
        shot = testShot();
        assertTrue(shot.getI() == 1 && shot.getJ() == 0);
        testShot();
        shot = testShot();
        assertTrue(shot.getI() == 5 && shot.getJ() == 0);
        testShot();
        shot = testShot();
        assertTrue(shot.getI() == 0 && shot.getJ() == 5);
        shot = testShot();
        assertTrue(shot.getI() == 9 && shot.getJ() == 0);
        for (int i = 0; i < 9; i++) {
            shot = testShot();
        }
        assertTrue(shot.getI() == 0 && shot.getJ() == 9);
        shot = testShot();
        assertTrue(shot.getI() == 9 && shot.getJ() == 4);
        for (int i = 0; i < 5; i++) {
            shot = testShot();
        }
        assertTrue(shot.getI() == 4 && shot.getJ() == 9);
        shot = testShot();
        assertTrue(shot.getI() == 9 && shot.getJ() == 8);
        shot = testShot();
        assertTrue(shot.getI() == 8 && shot.getJ() == 9); //проверили, что он перебьет все нужные клетки и перескочет через занятые, если нужно
    }

    public boolean checkShot() {
        for (int i = 0; i < GameField.SIZE; i++) {
            for (int j = 0; j < GameField.SIZE; j++) {
                if (field.getCell(i, j).isShot()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean shipContainsCell(Cell cell, Ship ship) {
        return ship.getDecks().contains(cell);
    }

    public Cell testShot() {
        Cell shot = ai.makeShot();
        if (shot.isEmpty()) {
            shot.setCondition(Condition.SHOT_WATER);
        }
        else if (shot.isNotShotDeck()) {
            shot.setCondition(Condition.SHIP_DAMAGED);
        }
        return shot;
    }
}