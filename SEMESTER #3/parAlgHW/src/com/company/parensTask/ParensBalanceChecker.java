package com.company.parensTask;
import com.company.scanner.PrefixScanner;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ParensBalanceChecker {

    public static boolean check(String test) {
        int cntBalance = 0;
        for (int i = 0; i < test.length(); i++) {
            if (test.charAt(i) == '(') {
                cntBalance++;
            }
            else if (test.charAt(i) == ')') {
                cntBalance--;
            }
            else {
                throw new IllegalArgumentException("Input Error!\n");

            }
            if (cntBalance < 0) {
                return false;
            }
        }
        return cntBalance == 0;
    }


    public static boolean parallelCheck(String test, int threadNum) throws InterruptedException {
        int testLen = test.length();
        Integer[] array = new Integer[testLen];
        ArrayList<ProcessingThread> threads = new ArrayList<>();
        for (int i = 0; i < threadNum; i++) {
            ProcessingThread thread = new ProcessingThread(Task.CONVERT_TO_INT, test, array, i, threadNum);
            threads.add(thread);
            thread.start();
        }
        for (ProcessingThread thread : threads) {
            thread.join();
        }
        threads.clear();

        Integer[] prefRes = new Integer[array.length];
        int cntBalance;
        PrefixScanner<Integer> prefixSumScanner = new PrefixScanner<>(array, prefRes, Integer::sum, 0);
        prefixSumScanner.scan(threadNum);

        for (int i = 0; i < threadNum; i++) {
            ProcessingThread thread = new ProcessingThread(Task.CHECK_PREFIXES, test, array, i, threadNum);
            threads.add(thread);
            thread.setPrefixes(prefRes);
            thread.start();
        }
        for (ProcessingThread thread : threads) {
            thread.join();
            if (!thread.arePrefixesCorrect()) {
                return false;
            }
        }
        cntBalance = prefRes[prefRes.length - 1];

        return cntBalance == 0;
    }
}
