package com.company;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        CounterHandler ch = new CounterHandler(0);
        Thread incTh = new Thread(() -> {
            for (int i = 0; i < 15; i++) {
                ch.inc();
                System.out.println("Inc 1: " + ch.getCnt());
            }

        });
        Thread decTh = new Thread(() -> {
            for (int i = 0; i < 25; i++) {
                ch.dec();
                System.out.println("Dec 1: " + ch.getCnt());
            }
        });
        Thread dblIncTh = new Thread(() -> {
            for (int i = 0; i < 60; i++) {
                ch.inc();
                ch.inc();
                System.out.println("Inc 2: " + ch.getCnt());
            }
        });
        Thread dblDecTh = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                ch.dec();
                ch.dec();
                System.out.println("Dec 2: " + ch.getCnt());
            }
        });

        incTh.start();
        decTh.start();
        dblIncTh.start();
        dblDecTh.start();

        decTh.join();
        incTh.join();
        dblDecTh.join();
        dblIncTh.join();


        System.out.println("End!");

    }


}
