package Cars;

import AbstractLib.Car;
import Main.Transmission;

public class Pickup extends Car {

    private int carrying;

    public Pickup(int maxSpeed, String color, String number, String producingCountry, int peopleCount, Transmission transmission, int carrying) {
        super(maxSpeed, color, number, producingCountry, peopleCount, transmission);
        this.carrying = carrying;
    }

    public int getCarrying() {
        return carrying;
    }

    public String getInformation() {
        String info = super.getInformation();
        info += ("\nCarrying: " + getCarrying());
        return info;
    }
}
