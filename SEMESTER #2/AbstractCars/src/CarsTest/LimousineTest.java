package CarsTest;

import Cars.Limousine;
import Main.Transmission;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LimousineTest {
    private Limousine limousine;

    @Before
    public void setUp() throws Exception {
        limousine = new Limousine(150, "YELLOW", "109-AAA", "China", 7, Transmission.AUTOMATIC, 5.75);
    }

    @Test
    public void getterLengthTest() {
        assertEquals(5.75, limousine.getLength(), 0.00001);
    }

    @Test
    public void getInformationTest() {
        String expected = "Number: 109-AAA\nProducing Country: China\nColor: YELLOW\nMax Speed: 150\nPeople count: 7\nTransmission: AUTOMATIC\nLength: 5.75";
        assertEquals(expected, limousine.getInformation());
    }
}