package com.company.workStealingSharing;

import java.util.Dictionary;
import java.util.Random;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ThreadLocalRandom;

public class WorkStealingThread extends Thread{
    Dictionary<Integer, IDEQueue<RecursiveAction>> queue;
    Random random;
    public WorkStealingThread(Dictionary<Integer, IDEQueue<RecursiveAction>> myQueue) {
        this.queue = myQueue;
    }
    @Override
    public void run() {
        int me = (int) Thread.currentThread().getId();
        RecursiveAction task = queue.get(me).popBottom();
        while (true) {
            while (task != null) {
                task.invoke();
                task = queue.get(me).popBottom();
            }
            while (task == null) {
                Thread.yield();
                int victim = ThreadLocalRandom.current().nextInt(queue.size());
                if (!queue.get(victim).isEmpty()) {
                    task = queue.get(victim).popTop();
                }
            }
        }
    }
}
