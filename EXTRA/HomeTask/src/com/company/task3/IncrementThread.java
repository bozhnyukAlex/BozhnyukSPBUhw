package com.company.task3;

public class IncrementThread extends Thread {
    private Counter counter;


    public IncrementThread(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for(int i = 0; i < 1000_000; i++) {
            counter.inc();
        }
    }
}
