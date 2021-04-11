package CarsTest;

import AbstractLib.Car;
import Cars.Limousine;
import Cars.Pickup;
import Main.Transmission;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AbstractCarTest {
    private Car carLim;
    private Car carPick;
    //@BeforeClass
    //@After
    //AfterClass
    @Before
    public void setUp() {
        carLim = new Limousine(150, "YELLOW", "109-AAA", "China", 7, Transmission.AUTOMATIC, 5.75);
        carPick = new Pickup(210, "WHITE", "666-XYZ", "India", 4, Transmission.MECHANIC, 1590);
    }
    //assertTrue(boolean expression)
    //assertArrayEquals()
    @Test
    public void getterMaxSpeedTest() {
        assertEquals(150, carLim.getMaxSpeed());
        assertEquals(210, carPick.getMaxSpeed());
    }

    @Test
    public void getterColorTest() {
        assertEquals("YELLOW", carLim.getColor());
        assertEquals("WHITE", carPick.getColor());
    }
    //
    /*
    @Test
    public void ColorWbTest(){

    }
     */

    @Test
    public void getterNumberTest() {
        assertEquals("109-AAA", carLim.getNumber());
        assertEquals("666-XYZ", carPick.getNumber());
    }

    @Test
    public void getterProducingCountryTest() {
        assertEquals("China", carLim.getProducingCountry());
        assertEquals("India", carPick.getProducingCountry());
    }

    @Test
    public void getterPeopleCountTest() {
        assertEquals(7, carLim.getPeopleCount());
        assertEquals(4, carPick.getPeopleCount());
    }

    @Test
    public void getterTransmissionTest() {
        assertEquals(Transmission.AUTOMATIC, carLim.getTransmission());
        assertEquals(Transmission.MECHANIC, carPick.getTransmission());
    }

    @Test
    public void setterColorTest() {
        carLim.setColor("BLUE");
        carPick.setColor("GOLD");
        assertEquals("BLUE", carLim.getColor());
        assertEquals("GOLD", carPick.getColor());
    }
}