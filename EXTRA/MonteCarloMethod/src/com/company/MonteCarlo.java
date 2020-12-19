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

    /*
    import scala.util.Random
    def mcCount(iter: Int): Int = {
        val randomX = new Random
        val randomY = new Random
        var hits = 0
        for (i <- 0 until iter) {
            val x = randomX.nextDouble // in [0,1]
            val y = randomY.nextDouble // in [0,1]
            if (x*x + y*y < 1) hits= hits + 1
        }
        hits
     }
    def monteCarloPiSeq(iter: Int): Double = 4.0 * mcCount(iter) / iter
     */

    /*
    def monteCarloPiPar(iter: Int): Double = {
        val ((pi1, pi2), (pi3, pi4)) = parallel(
                parallel(mcCount(iter/4), mcCount(iter/4)),
                parallel(mcCount(iter/4), mcCount(iter - 3*(iter/4))))
        4.0 * (pi1 + pi2 + pi3 + pi4) / iter
    }
     */


}
