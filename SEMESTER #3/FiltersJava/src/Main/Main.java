package Main;

import Picture.*;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
      //  System.out.println(System.getProperty("user.dir"));

        if (args.length != 3 || !(args[1].equals("Average3x3") || args[1].equals("Gauss3x3") || args[1].equals("Gauss5x5") || args[1].equals("SobelX") || args[1].equals("SobelY") || args[1].equals("ColorWB"))) {
            System.out.println("Input Error!");
            System.exit(-1);
        }
        Picture picture = new Picture();
        picture.Read(args[0]);
        Filter filter = new Filter(args[1]);
        filter.use(picture);
        picture.Write(args[2]);
        System.out.println("Success\n");
    }

    public static int toLittleEndian(int x) {
        byte[] xb = new byte[] {
            (byte)((x >> 24) & 0xff),
            (byte)((x >> 16) & 0xff),
            (byte)((x >> 8) & 0xff),
            (byte)(x & 0xff),
        };
        return ((xb[3] & 0xFF) << 24) + ((xb[2] & 0xFF) << 16) + ((xb[1] & 0xFF) << 8) + (xb[0] & 0xFF);
    }
    public static short toLittleEndian(short x) {
        byte[] xb = new byte[] {
                (byte)((x >> 8) & 0xff),
                (byte)(x & 0xff),
        };
        return (short) (((xb[1] & 0xFF) << 8) + (xb[0] & 0xFF));
    }

}
