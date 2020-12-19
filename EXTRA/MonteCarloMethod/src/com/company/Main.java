package com.company;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        System.out.println(MonteCarlo.getPi(4));
        MonteCarlo.iter = 52428800;
        System.out.println(MonteCarlo.getPi(16));
    }
}
