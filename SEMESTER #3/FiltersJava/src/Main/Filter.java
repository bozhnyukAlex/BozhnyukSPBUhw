package Main;

import Picture.*;

public class Filter {
    private String filterLabel;
    private int divisor;
    private int[] mask;
    private int border;
    private boolean isSobel;

    public Filter(String filterLabel) {
        this.filterLabel = filterLabel;
        switch (filterLabel) {
            case "Average3x3": {
                mask = new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1};
                divisor = 9;
                isSobel = false;
                border = 1;
                break;
            }
            case "Gauss3x3": {
                mask = new int[] {1, 2, 1, 2, 4, 2, 1, 2, 1};
                divisor = 16;
                isSobel = false;
                border = 1;
                break;
            }
            case "Gauss5x5": {
                mask = new int[] {
                    1, 4, 6, 4, 1,
                    4, 16, 24, 16, 4,
                    6, 24, 36, 24, 6,
                    4, 16, 24, 16, 4,
                    1, 4, 6, 4, 1
                };
                divisor = 256;
                isSobel = false;
                border = 2;
                break;
            }
            case "SobelX": {
                mask = new int[] {-1, -2, -1, 0, 0, 0, 1, 2, 1};
                divisor = 1;
                isSobel = true;
                border = 1;
                break;
            }
            case "SobelY": {
                mask = new int[] {-1, 0, 1, -2, 0, 2, -1 ,0, 1};
                divisor = 1;
                isSobel = true;
                border = 1;
                break;
            }
        }
    }

    public void use(Picture picture) {
        RGB[][] copy = new RGB[picture.getHeight()][picture.getWidth()];
        for (int i = 0; i < picture.getHeight(); i++) {
            for (int j = 0; j < picture.getWidth(); j++) {
                copy[i][j] = new RGB();
            }
        }

        if (filterLabel.equals("ColorWB")) {
            for (int i = 0; i < picture.getHeight(); i++) {
                for (int j = 0; j < picture.getWidth(); j++) {
                    RGB cur = picture.getPixel(i, j);
                    int result = (((cur.getRed() + cur.getGreen() + cur.getBlue()) / 3) & 0xFF);
                    cur.setRed(result);
                    cur.setGreen(result);
                    cur.setBlue(result);
                }
            }
            return;
        }
        int redRes = 0, greenRes = 0, blueRes = 0, count = 0;
        for (int i = border; i < picture.getHeight() - border; i++) {
            for (int j = border; j < picture.getWidth() - border; j++) {
                redRes = greenRes = blueRes = count = 0;
                for (int v = -border; v <= border; v++) {
                    for (int w = -border; w <= border; w++) {
                        redRes += (mask[count] * picture.getPixel(i + v, j + w).getRed());
                        greenRes += (mask[count] * picture.getPixel(i + v, j + w).getGreen());
                        blueRes += (mask[count] * picture.getPixel(i + v, j + w).getBlue());
                        count++;
                    }
                }
                redRes /= divisor;
                greenRes /= divisor;
                blueRes /= divisor;
                if (isSobel) {
                    int res = ((redRes + blueRes + greenRes) / 3);
                    //System.out.println(res);
                    res = (res > 128)? 255 : 0;
                    copy[i][j].setRed(res);
                    copy[i][j].setGreen(res);
                    copy[i][j].setBlue(res);
                }
                else {
                    copy[i][j].setRed(redRes);
                    copy[i][j].setGreen(greenRes);
                    copy[i][j].setBlue(blueRes);
                }

            }
        }
        for (int i = 0; i < picture.getHeight(); i++) {
            for (int j = 0; j < picture.getWidth(); j++) {
                picture.setPixel(i, j, copy[i][j]);
            }
        }

    }
}
