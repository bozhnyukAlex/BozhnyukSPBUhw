package Main;

import Picture.*;
import Threads.ApplyRowFilterThread;
import Threads.CopyRowInitThread;
import Threads.SetPixThread;

import java.util.ArrayList;

public class Filter {
    private String filterLabel;
    private int divisor;
    private int[] mask;
    private int border;
    private boolean isSobel;

    public Filter(String filterLabel) {
        this.filterLabel = filterLabel;
        switch (filterLabel) {
            case "ColorWB": {
                border = 0;
                isSobel = false;
                divisor = -1;
                break;
            }
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
        int redRes, greenRes, blueRes, count;
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

    public void parallelUse(Picture picture) throws Exception {
        RGB[][] copy = new RGB[picture.getHeight()][picture.getWidth()];
        ArrayList<Thread> rawInitThreads = new ArrayList<>();
        for (int i = 0; i < picture.getHeight(); i++) {
            Thread curThr = new CopyRowInitThread(copy, i, picture.getWidth());
            rawInitThreads.add(curThr);
            curThr.start();
        }
        for (Thread rawInitThread : rawInitThreads) {
            rawInitThread.join();
        }

        ArrayList<Thread> applyThreads = new ArrayList<>();
        for (int i = border; i < picture.getHeight() - border; i++) {
            Thread curThr = new ApplyRowFilterThread(picture, this, i, copy);
            applyThreads.add(curThr);
            curThr.start();
        }
        for (Thread appThread : applyThreads) {
            appThread.join();
        }
        if (filterLabel.equals("ColorWB")) {
            return;
        }
        ArrayList<Thread> setterThreads = new ArrayList<>();
        for (int i = 0; i < picture.getHeight(); i++) {
            Thread curTh = new SetPixThread(picture, copy, i);
            setterThreads.add(curTh);
            curTh.start();
        }

        for (Thread setThread : setterThreads) {
            setThread.join();
        }

    }

    public String getFilterLabel() {
        return filterLabel;
    }

    public int getDivisor() {
        return divisor;
    }

    public int[] getMask() {
        return mask;
    }

    public int getBorder() {
        return border;
    }

    public boolean isSobel() {
        return isSobel;
    }
}
