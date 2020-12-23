package com.company.task3;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        IncrementThread[] threads = new IncrementThread[3];
        Counter cnt = new Counter(0);
        for (int i = 0; i < 3; i++) {
            threads[i] = new IncrementThread(cnt);
            threads[i].start();
        }
        for (int i = 0; i < 3; i++) {
            threads[i].join();
        }
        System.out.println(cnt.getCounter());
    }
}
