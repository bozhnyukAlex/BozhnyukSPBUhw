package Cars;

import AbstractLib.Car;
import Main.Transmission;

public class Limousine extends Car {
    private double length;
    public Limousine(int maxSpeed, String color, String number, String producingCountry, int peopleCount, Transmission transmission, double length) {
        super(maxSpeed, color, number, producingCountry, peopleCount, transmission);
        this.length = length;
    }

    public double getLength() {
        return length;
    }

    public String getInformation() {
        String info = super.getInformation();
        info += ("\nLength: " + getLength());
        return info;
    }
}
