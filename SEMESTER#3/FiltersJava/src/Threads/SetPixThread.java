package Threads;

import Picture.*;

public class SetPixThread extends Thread {
    private Picture picture;
    private RGB[][] copy;
    private int startIncl;
    private int endExcl;
    private boolean isHorizontal;

    public SetPixThread(Picture picture, int start, int end , RGB[][] copy, boolean isHor) {
        this.picture = picture;
        this.copy = copy;
        this.startIncl = start;
        this.endExcl = end;
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
                picture.setPixel(firstDim, secondDim, copy[firstDim][secondDim]);
            }
        }
    }
}
