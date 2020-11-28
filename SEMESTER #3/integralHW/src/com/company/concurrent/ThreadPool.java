package com.company.concurrent;

import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool {
    private int nThreads;
    private PoolWorker[] threads;
    private LinkedBlockingQueue<Runnable> queue;
    private boolean isFinished;
    private boolean isShuttedDown;


    public ThreadPool(int nThreads) {
        this.nThreads = nThreads;
        queue = new LinkedBlockingQueue<>();
        threads = new PoolWorker[nThreads];
        isFinished = false;
        isShuttedDown = false;
        for (int i = 0; i < nThreads; i++) {
            threads[i] = new PoolWorker();
            threads[i].start();
        }

    }

    public void execute (Runnable task) {
        if (isShuttedDown) {
            return;
        }
        synchronized (queue) {
            queue.add(task);
            queue.notify();
        }
    }

    public void shutdownNow() {
        isFinished = true;
    }

    public void shutdown() {
        isShuttedDown = true;
    }

    private class PoolWorker extends Thread {
        public void run() {
            Runnable task;
            while (!isFinished) {
                if (isShuttedDown && queue.isEmpty()) {
                    break;
                }
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        if (isShuttedDown) {
                            break;
                        }
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            System.out.println("An error occurred while queue is waiting: " + e.getMessage());
                        }
                    }
                    task = queue.poll();
                }
                try {
                    if (task != null) {
                        task.run();
                    }

                } catch (RuntimeException e) {
                    System.out.println("Thread pool is interrupted due to an issue: " + e.getMessage());
                }
            }
        }
    }
}
