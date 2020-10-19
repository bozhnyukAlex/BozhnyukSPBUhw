package Threads;

import Picture.*;

public class SetPixThread extends Thread {
    private Picture picture;
    private RGB[][] copy;
    private int rowNum;

    public SetPixThread(Picture picture, RGB[][] copy, int rowNum) {
        this.picture = picture;
        this.copy = copy;
        this.rowNum = rowNum;
    }

    @Override
    public void run() {
        for (int j = 0; j < picture.getWidth(); j++) {
            picture.setPixel(rowNum, j, copy[rowNum][j]);
        }
    }
}
