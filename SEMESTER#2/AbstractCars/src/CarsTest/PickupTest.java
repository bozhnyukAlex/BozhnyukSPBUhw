package CarsTest;

import Cars.Pickup;
import Main.Transmission;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PickupTest {
    private Pickup pickup;

    @Before
    public void setUp() throws Exception {
        pickup = new Pickup(210, "WHITE", "666-XYZ", "India", 4, Transmission.AUTOMATIC, 1590);
    }

    @Test
    public void getterCarryingTest() {
        assertEquals(1590, pickup.getCarrying());
    }

    @Test
    public void getInformationTest() {
        String expected = "Number: 666-XYZ\nProducing Country: India\nColor: WHITE\nMax Speed: 210\nPeople count: 4\nTransmission: AUTOMATIC\nCarrying: 1590";
        assertEquals(expected, pickup.getInformation());
    }
}