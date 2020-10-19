package Threads;

import Picture.RGB;

public class CopyRowInitThread extends Thread {
    int rowNum;
    RGB[][] pane;
    int picWidth;

    public CopyRowInitThread(RGB[][] rgb, int row, int picWidth) {
        this.pane = rgb;
        this.rowNum = row;
        this.picWidth = picWidth;
    }

    @Override
    public void run() {
        for (int j = 0; j < picWidth; j++) {
            pane[rowNum][j] = new RGB();
        }
    }
}
