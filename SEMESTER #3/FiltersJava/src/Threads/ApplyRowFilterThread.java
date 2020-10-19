package Threads;

import Main.Filter;
import Picture.*;


public class ApplyRowFilterThread extends Thread{
    private int rowNum;
    private Picture picture;
    private Filter filter;
    private RGB[][] copy;




    public ApplyRowFilterThread(Picture picture, Filter filter, int row, RGB[][] copy) {
        this.copy = copy;
        this.filter = filter;
        this.rowNum = row;
        this.picture = picture;
    }

    @Override
    public void run() {
        if (filter.getFilterLabel().equals("ColorWB")) {
            for (int j = 0; j < picture.getWidth(); j++) {
                RGB cur = picture.getPixel(rowNum, j);
                int result = (((cur.getRed() + cur.getGreen() + cur.getBlue()) / 3) & 0xFF);
                cur.setRed(result);
                cur.setGreen(result);
                cur.setBlue(result);
            }
            return;
        }
        int border = filter.getBorder();
        int divisor = filter.getDivisor();
        int[] mask = filter.getMask();
        boolean isSobel = filter.isSobel();

        int redRes, greenRes, blueRes, count;
        for (int j = border; j < picture.getWidth() - border; j++) {
            redRes = greenRes = blueRes = count = 0;
            for (int v = -border; v <= border; v++) {
                for (int w = -border; w <= border; w++) {
                    redRes += (mask[count] * picture.getPixel(rowNum + v, j + w).getRed());
                    greenRes += (mask[count] * picture.getPixel(rowNum + v, j + w).getGreen());
                    blueRes += (mask[count] * picture.getPixel(rowNum + v, j + w).getBlue());
                    count++;
                }
            }
            redRes /= divisor;
            greenRes /= divisor;
            blueRes /= divisor;
            if (isSobel) {
                int res = ((redRes + blueRes + greenRes) / 3);
                res = (res > 128)? 255 : 0;
                copy[rowNum][j].setRed(res);
                copy[rowNum][j].setGreen(res);
                copy[rowNum][j].setBlue(res);
            }
            else {
                copy[rowNum][j].setRed(redRes);
                copy[rowNum][j].setGreen(greenRes);
                copy[rowNum][j].setBlue(blueRes);
            }
        }
    }
}
