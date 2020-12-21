package Threads;

import Main.Filter;
import Picture.*;


public class ApplyFilterThread extends Thread {
    private Picture picture;
    private Filter filter;
    private RGB[][] copy;
    private int startIncl;
    private int endExcl;
    private boolean isHorizontal;




    public ApplyFilterThread(Picture picture, Filter filter, RGB[][] copy, int start, int end, boolean isHor) {
        this.copy = copy;
        this.filter = filter;
        this.picture = picture;
        this.startIncl = start;
        this.endExcl = end;
        this.isHorizontal = isHor;
    }

    @Override
    public void run() {
        int secondCycleLimit = (isHorizontal)? picture.getWidth() : picture.getHeight();
        int firstDim, secondDim;
        if (filter.getFilterLabel().equals("ColorWB")) {
            for (int i = startIncl; i < endExcl; i++) {
                for (int j = 0; j < secondCycleLimit; j++) {
                    firstDim = (isHorizontal)? i : j;
                    secondDim = (isHorizontal)? j : i;
                    RGB cur = picture.getPixel(firstDim, secondDim);
                    int result = (((cur.getRed() + cur.getGreen() + cur.getBlue()) / 3) & 0xFF);
                    cur.setRed(result);
                    cur.setGreen(result);
                    cur.setBlue(result);
                }
            }
            return;
        }
        int border = filter.getBorder();
        int divisor = filter.getDivisor();
        int[] mask = filter.getMask();
        boolean isSobel = filter.isSobel();

        int redRes, greenRes, blueRes, count;
        for (int i = startIncl; i < endExcl; i++) {
            for (int j = border; j < secondCycleLimit - border; j++) {
                firstDim = (isHorizontal)? i : j;
                secondDim = (isHorizontal)? j : i;
                redRes = greenRes = blueRes = count = 0;
                for (int v = -border; v <= border; v++) {
                    for (int w = -border; w <= border; w++) {
                        redRes += (mask[count] * picture.getPixel(firstDim + v, secondDim + w).getRed());
                        greenRes += (mask[count] * picture.getPixel(firstDim + v, secondDim + w).getGreen());
                        blueRes += (mask[count] * picture.getPixel(firstDim + v, secondDim + w).getBlue());
                        count++;
                    }
                }
                redRes /= divisor;
                greenRes /= divisor;
                blueRes /= divisor;
                if (isSobel) {
                    int res = ((redRes + blueRes + greenRes) / 3);
                    res = (res > 128) ? 255 : 0;
                    copy[firstDim][secondDim].setRed(res);
                    copy[firstDim][secondDim].setGreen(res);
                    copy[firstDim][secondDim].setBlue(res);
                } else {
                    copy[firstDim][secondDim].setRed(redRes);
                    copy[firstDim][secondDim].setGreen(greenRes);
                    copy[firstDim][secondDim].setBlue(blueRes);
                }
            }
        }
    }
}
