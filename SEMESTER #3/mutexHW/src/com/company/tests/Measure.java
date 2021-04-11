package com.company.tests;

import com.company.locks.TASLock;
import com.company.locks.TTASLock;
import com.company.main.CounterHandler;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class Measure {
    private CounterHandler counterTAS;
    private CounterHandler counterTTAS;
    private ArrayList<Thread> threadListTAS;
    private ArrayList<Thread> threadListTTAS;

    @Param({"1", "2", "4", "8", "16", "32", "64"})
    public int nThread;

    @Benchmark
    public void  measureTAS() throws InterruptedException {
        counterTAS = new CounterHandler(0, new TASLock());
        threadListTAS = new ArrayList<>();
        for (int i = 0; i < nThread; i++) {

            Thread currTAS = new Thread(() -> {
                while (counterTAS.getCnt() < 100000) {
                    counterTAS.inc();
                }
            });

            threadListTAS.add(currTAS);
            currTAS.start();

        }
        for (Thread thread : threadListTAS) {
            thread.join();
        }
    }
    @Benchmark
    public void measureTTAS() throws InterruptedException {
        counterTTAS = new CounterHandler(0, new TTASLock());
        threadListTTAS = new ArrayList<>();

        for (int i = 0; i < nThread; i++) {
            Thread currTTAS = new Thread(() -> {
                while (counterTTAS.getCnt() < 100000) {
                    counterTTAS.inc();
                }
            });

            threadListTTAS.add(currTTAS);
            currTTAS.start();

        }
        for (Thread thread : threadListTTAS) {
            thread.join();
        }
    }
}
