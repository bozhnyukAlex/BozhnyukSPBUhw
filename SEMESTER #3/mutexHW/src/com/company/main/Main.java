package com.company.main;

import com.company.locks.PetersonLock;
import com.company.main.CounterHandler;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        CounterHandler ch = new CounterHandler(0, new PetersonLock());
        Thread incTh = new Thread(() -> {
            for (int i = 0; i < 16; i++) {
                ch.inc();
                System.out.println("Inc 1: " + ch.getCnt());
            }

        });
        Thread decTh = new Thread(() -> {
            for (int i = 0; i < 16; i++) {
                ch.dec();
                System.out.println("Dec 1: " + ch.getCnt());
            }
        });
        decTh.setName("0");
        incTh.setName("1");
        decTh.start();
        incTh.start();
        decTh.join();
        incTh.join();

        System.out.println("End!");

    }


}
