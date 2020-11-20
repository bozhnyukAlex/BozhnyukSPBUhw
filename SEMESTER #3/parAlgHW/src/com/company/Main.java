package com.company;

import com.company.scanner.PrefixScanner;
import com.company.sumTask.Summator;

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

        System.out.println("");
        Summator summator = new Summator();
        int[] res = summator.sum(new int[] {9}, new int[] {9, 9, 9, 9, 9});
        System.out.print("Sum not parallel res: ");
        for (int i = 0; i < res.length; i++) {
            System.out.print(res[i]);
        }
        System.out.println("Parallel sum: ");

        int[] res1 = summator.parallelSum(new int[] {9,9,9,9,9}, new int[] {9,9,9,9,9}, 2);
        for (int i = 0; i < res.length; i++) {
            System.out.print(res1[i]);
        }
        System.out.println("");

    }
}
