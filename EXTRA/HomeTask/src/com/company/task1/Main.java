package com.company.task1;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            Thread thread = new PrintNameDateThread();
            thread.start();
            thread.join();
            Thread.sleep(3000);
        }
    }
}
