package com.company.workStealingSharing;

import java.util.Dictionary;
import java.util.Queue;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ThreadLocalRandom;

public class WorkSharingThread extends Thread {

    Dictionary<Integer, Queue<RecursiveAction>> queue;
    private static final int THRESHOLD = 42;
    public WorkSharingThread(Dictionary<Integer, Queue<RecursiveAction>> queue) {
        this.queue = queue;
    }
    @Override
    public void run() {
        int me = (int) Thread.currentThread().getId();
        while (true) {
            RecursiveAction task = null;
            synchronized (queue.get(me)) {
                queue.get(me).remove();
            }
            if (task != null) {
                task.invoke();
            }
            int size = queue.get(me).size();
            if (ThreadLocalRandom.current().nextInt(size+1) == size) {
                int victim = ThreadLocalRandom.current().nextInt(queue.size());
                int min = (victim <= me) ? victim : me;
                int max = (victim <= me) ? me : victim;
                synchronized (queue.get(min)) {
                    synchronized (queue.get(max)) {
                        balance(queue.get(min), queue.get(max));
                    }
                }
            }
        }
    }
    private void balance(Queue<RecursiveAction> q0, Queue<RecursiveAction> q1) {
        Queue<RecursiveAction> qMin = (q0.size() < q1.size()) ? q0 : q1;
        Queue<RecursiveAction> qMax = (q0.size() < q1.size()) ? q1 : q0;
        int diff = qMax.size() - qMin.size();
        if (diff > THRESHOLD) {
            while (qMax.size() > qMin.size()) {
                qMin.add(qMax.remove());
            }
        }
    }
}
