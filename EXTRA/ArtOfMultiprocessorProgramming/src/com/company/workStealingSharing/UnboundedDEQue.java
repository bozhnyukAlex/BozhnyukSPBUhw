package com.company.workStealingSharing;

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicReference;

public class UnboundedDEQue {
    private final static int LOG_CAPACITY = 4;
    private volatile CircularArray tasks;
    volatile int bottom;
    AtomicReference<Integer> top;
    public UnboundedDEQue(int logCapacity) {
        tasks = new CircularArray(logCapacity);
        top = new AtomicReference<Integer>(0);
        bottom = 0;
    }
    boolean isEmpty() {
        int localTop = top.get();
        int localBottom = bottom;
        return (localBottom <= localTop);
    }

    public void pushBottom(RecursiveAction r) {
        int oldBottom = bottom;
        int oldTop = top.get();
        CircularArray currentTasks = tasks;
        int size = oldBottom - oldTop;
        if (size >= currentTasks.capacity() - 1) {
            currentTasks = currentTasks.resize(oldBottom, oldTop);
            tasks = currentTasks;
        }
        currentTasks.put(oldBottom, r);
        bottom = oldBottom + 1;
    }

    public RecursiveAction popTop() {
        int oldTop = top.get();
        int newTop = oldTop + 1;
        int oldBottom = bottom;
        CircularArray currentTasks = tasks;
        int size = oldBottom - oldTop;
        if (size <= 0) return null;
        RecursiveAction r = tasks.get(oldTop);
        if (top.compareAndSet(oldTop, newTop)) {
            return r;
        }
        return null;
    }
    public RecursiveAction popBottom() {
        int newBottom = --bottom;
        int oldTop = top.get();
        int newTop = oldTop + 1;
        int size = newBottom - oldTop;
        if (size < 0) {
            bottom = oldTop;
            return null;
        }
        RecursiveAction r = tasks.get(newBottom);
        if (size > 0){
            return r;
        }
        if (!top.compareAndSet(oldTop, newTop)){
            r = null;
        }
        bottom = newTop;
        return r;
    }
}
