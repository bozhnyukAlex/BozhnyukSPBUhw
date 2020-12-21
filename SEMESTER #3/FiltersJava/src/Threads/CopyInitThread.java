package Threads;

import Picture.Picture;
import Picture.RGB;

public class CopyInitThread extends Thread {
    private RGB[][] pane;
    private Picture picture;
    private int startIncl;
    private int endExcl;
    private boolean isHorizontal;

    public CopyInitThread(RGB[][] rgb, int start, int end, Picture picture, boolean isHor) {
        this.pane = rgb;
        this.startIncl = start;
        this.endExcl = end;
        this.picture = picture;
        this.isHorizontal = isHor;
    }

    @Override
    public void run() {
        int firstDim, secondDim;
        int secondCycleLimit = (isHorizontal)? picture.getWidth() : picture.getHeight();
        for (int i = startIncl; i < endExcl; i++) {
            for (int j = 0; j < secondCycleLimit; j++) {
                firstDim = (isHorizontal)? i : j;
                secondDim = (isHorizontal)? j : i;
                pane[firstDim][secondDim] = new RGB();
            }
        }
    }
}
