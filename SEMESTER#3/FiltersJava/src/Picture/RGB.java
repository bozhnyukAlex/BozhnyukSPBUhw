package Picture;

import Main.Main;

public class RGB {
    private int red;
    private int green;
    private int blue;

    public RGB() {
        this.red = 255;
        this.green = 255;
        this.blue = 255;
    }


    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }
}
