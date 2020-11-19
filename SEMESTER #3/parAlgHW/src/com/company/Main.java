package com.company;

import com.company.scanner.PrefixScanner;

import java.util.concurrent.ExecutionException;
import java.util.function.BinaryOperator;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int sz = 12;
        Integer[] test = new Integer[sz];
        for (int i = 0; i < sz; i++) {
            test[i] = i + 1;
        }
        Integer[] result = new Integer[sz];
        BinaryOperator<Integer> sum = Integer::sum;
        PrefixScanner<Integer> prefixSumScanner = new PrefixScanner<>(test, result, Integer::sum, 0);
        prefixSumScanner.scan(4);
        for (Integer integer : result) {
            System.out.print(integer + " ");
        }
    }
}
