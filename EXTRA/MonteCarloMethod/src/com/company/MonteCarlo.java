package com.company;

import java.security.KeyPair;
import java.util.ArrayList;

public class MonteCarlo {

    public static long iter = 204800;

    public static double getPi(int threadNum) throws InterruptedException {
        ArrayList<CountThread> threads = new ArrayList<>();
        for (int i = 0; i < threadNum; i++) {
            CountThread thread = new CountThread(iter / threadNum);
            threads.add(thread);
            thread.start();
        }
        for (CountThread thread : threads) {
            thread.join();
        }
        return 4.0 * threads.parallelStream().map(CountThread::getHits).reduce(0L, Long::sum) / iter;
    }


}
