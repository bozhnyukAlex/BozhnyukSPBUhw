package com.company;

import java.util.Random;
import java.util.concurrent.Callable;

public class CountThread extends Thread {
    private long iter;
    private long hits;

    public CountThread(long iter) {
        this.iter = iter;
        this.hits = 0;
    }


    @Override
    public void run()  {
        Random randomX = new Random();
        Random randomY = new Random();
        for (int i = 0; i <= iter; i++) {
            double x = randomX.nextDouble();
            double y = randomY.nextDouble();
            if (x * x + y * y < 1) {
                hits++;
            }
        }
    }

    public long getHits() {
        return hits;
    }
}
