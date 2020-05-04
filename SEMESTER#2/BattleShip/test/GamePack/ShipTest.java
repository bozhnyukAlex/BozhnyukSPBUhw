package GamePack;

import org.app.Config;
import org.junit.Before;
import org.junit.Test;
import org.game.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ShipTest {
    private Ship s4, s3, s2, s1;
    @Before
    public void setUp() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        //s4 = new Ship(4);
        s4 = context.getBean("ship4", Ship.class);
        //s3 = new Ship(3);
        s3 = context.getBean("ship3", Ship.class);
        s2 = new Ship(new Cell(0,0), new Cell(0, Cell.SIZE));
        //s1 = new Ship(1);
        s1 = context.getBean("ship1", Ship.class);

    }

    @Test
    public void buildTest() {
        Cell[] decks = new Cell[] {new Cell(7*Cell.SIZE,0), new Cell(7 * Cell.SIZE, Cell.SIZE), new Cell(7 * Cell.SIZE, 2 * Cell.SIZE), new Cell(7 * Cell.SIZE, 3 * Cell.SIZE)};
        s4.build(decks);
        int i = 0;
        for (Cell deck : s4.getDecks()) {
            assertEquals(decks[i], deck);
            i++;
        }
    }

    @Test
    public void getDamageTest() {
        assertEquals(s4.getLength(), 4);
        s4.getDamage();
        assertEquals(s4.getLength(), 3);
    }

    @Test
    public void getDecksTest() {
        Cell[] decks = new Cell[] {new Cell(8 * Cell.SIZE,7), new Cell(8 * Cell.SIZE, 7 + Cell.SIZE), new Cell(8 * Cell.SIZE, 7 + 2 * Cell.SIZE)};
        ArrayList<Cell> decksShip = s3.getDecks();
        int i = 0;
        for (Cell deck : decksShip) {
            assertEquals(deck, decks[i]);
            i++;
        }
    }

    @Test
    public void hasDeckWithThisCoordinatesTest() {
        assertFalse(s2.hasDeckWithThisCoordinates(0, 6));
        assertTrue(s2.hasDeckWithThisCoordinates(0, 0));
    }

    @Test
    public void isDestroyedTest() {
        assertFalse(s1.isDestroyed());
        s1.getDamage();
        assertTrue(s1.isDestroyed());

        assertFalse(s4.isDestroyed());
        for (int i = 0; i < 4; i++) {
            s4.getDamage();
        }
        assertTrue(s4.isDestroyed());
    }
}