package com.company.sequenceTask;

import com.company.scanner.ParallelScanner;

public class SequenceFinder {
    public static int find(Pair[] array) {
        int x = 0;
        for (Pair pair : array) {
            x = x * pair.left + pair.right;
        }
        return x;
    }
    public static int findParallel(Pair[] array, int threadNum) throws InterruptedException {
        Pair total = new Pair(1, 2);
        ParallelScanner<Pair> pairParallelScanner = new ParallelScanner<>(array, total, Pair::operator);
        pairParallelScanner.scan(threadNum);
        return pairParallelScanner.getTotal().right;
    }
}
