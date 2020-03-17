package Main;

import Cars.Limousine;
import Cars.Pickup;

public class Main {
    public static void main(String[] args) throws Exception {
        Pickup teslaCyberTruck = new Pickup(209, "Gray", "116-LYK", "America", 4, Transmission.AUTOMATIC, 1588);
        Limousine rollsRoycePhantom = new Limousine(150, "Black", "ZAU-838", "England", 8, Transmission.AUTOMATIC, 2.35);
        System.out.println("Information about first Car:");
        System.out.println(teslaCyberTruck.getInformation());
        System.out.println("\nInformation about second Car:");
        System.out.println(rollsRoycePhantom.getInformation());
    }

}
