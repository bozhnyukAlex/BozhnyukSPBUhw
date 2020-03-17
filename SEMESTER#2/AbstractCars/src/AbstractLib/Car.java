package AbstractLib;

import Main.Transmission;

public abstract class Car {
    private int maxSpeed;
    private String color;
    private String number;
    private String producingCountry;
    private int peopleCount;
    private Transmission transmission;

    public Car(int maxSpeed, String color, String number, String producingCountry, int peopleCount, Transmission transmission) {
        this.maxSpeed = maxSpeed;
        this.color = color;
        this.number = number;
        this.producingCountry = producingCountry;
        this.peopleCount = peopleCount;
        this.transmission = transmission;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public String getColor() {
        return color;
    }

    public String getNumber() {
        return number;
    }

    public String getProducingCountry() {
        return producingCountry;
    }

    public int getPeopleCount() {
        return peopleCount;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getInformation() {
        String info = "Number: " + getNumber() + "\nProducing Country: " + getProducingCountry() + "\nColor: " + getColor()
                            + "\nMax Speed: " + getMaxSpeed() + "\nPeople count: " + getPeopleCount() + "\nTransmission: " + getTransmission();
        return info;
    }
}

