package com.company;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.function.BinaryOperator;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ArrayList<Integer> test = new ArrayList<Integer>();
        for (int i = 1; i <= 14; i++) {
            test.add(i);
        }

        BinaryOperator<Integer> sum = Integer::sum;

        ReduceScanner<Integer> sumScanner = new ReduceScanner<>(test, sum, 0);
        sumScanner.parallelScan(4);
        for (int i = 0; i < sumScanner.getBuffer().size(); i++) {
            System.out.print(sumScanner.getBuffer().get(i) + " ");
        }
    }
}
