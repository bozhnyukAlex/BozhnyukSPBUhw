package com.company.main;

import com.company.parensTask.ParensBalanceChecker;
import com.company.turtleTask.StepPair;
import com.company.turtleTask.TurtleLocator;

import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int sz = 12;
        Integer[] test = new Integer[sz];
        for (int i = 0; i < sz; i++) {
            test[i] = i + 1;
        }
        Integer[] result = new Integer[sz];
        /*BinaryOperator<Integer> sum = Integer::sum;
        PrefixScanner<Integer> prefixSumScanner = new PrefixScanner<>(test, result, Integer::sum, 0);
        prefixSumScanner.scan(4);
        for (Integer integer : result) {
            System.out.print(integer + " ");
        }

        System.out.println("");

        int[] res = Adder.add(new int[] {9}, new int[] {9, 9, 9, 9, 9});
        System.out.print("Sum not parallel res: ");
        for (int i = 0; i < res.length; i++) {
            System.out.print(res[i]);
        }
        System.out.println("Parallel sum: ");

        int[] res1 = Adder.parallelAdd(new int[] {9,9,9,9,9}, new int[] {9,9,9,9,9}, 2);
        for (int i = 0; i < res.length; i++) {
            System.out.print(res1[i]);
        }
        System.out.println("");*/
       /* Integer total = 0;
        ParallelScanner<Integer> parallelScanner = new ParallelScanner<>(test, total, Integer::sum);
        parallelScanner.scan(4);
        total = parallelScanner.getTotal();
        System.out.println(total);*/

        /*Pair[] pairs = new Pair[] {new Pair(0,3), new Pair(1, 2), new Pair(2, 3)};
        int total = SequenceFinder.parallelFind(pairs, 2);
        System.out.println(total);
        */

       /* String s = "((()(()))";
        System.out.println(ParensBalanceChecker.check(s) + " " + ParensBalanceChecker.checkParallel(s, 4));*/


    }
}
