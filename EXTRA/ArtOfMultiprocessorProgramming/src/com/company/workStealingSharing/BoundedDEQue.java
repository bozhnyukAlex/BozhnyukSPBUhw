package com.company.workStealingSharing;

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicStampedReference;

public class BoundedDEQue {
    RecursiveAction[] tasks;
    volatile int bottom;
    AtomicStampedReference<Integer> top;
    public BoundedDEQue(int capacity) {
        tasks = new RecursiveAction[capacity];
        top = new AtomicStampedReference<Integer>(0, 0);
        bottom = 0;
    }
    public void pushBottom(RecursiveAction r){
        tasks[bottom] = r;
        bottom++;
    }
    // called by thieves to determine whether to try to steal
    public boolean isEmpty() {
        return (bottom <= top.getReference());
    }

    public RecursiveAction popTop() {
        int[] stamp = new int[1];
        int oldTop = top.get(stamp);
        int newTop = oldTop + 1;
        int oldStamp = stamp[0];
        if (bottom <= oldTop) {
            return null;
        }
        RecursiveAction r = tasks[oldTop];
        if (top.compareAndSet(oldTop, newTop, oldStamp, oldStamp)) {
            return r;
        }
        else {
            return null;
        }
    }
    public RecursiveAction popBottom() {
        if (bottom == 0) {
            return null;
        }
        int newBottom = --bottom;
        RecursiveAction r = tasks[newBottom];
        int[] stamp = new int[1];
        int oldTop = top.get(stamp);
        int newTop = 0;
        int oldStamp = stamp[0];
        int newStamp = oldStamp + 1;
        if (newBottom > oldTop) {
            return r;
        }
        if (newBottom == oldTop) {
            bottom = 0;
            if (top.compareAndSet(oldTop, newTop, oldStamp, newStamp))
                return r;
            }
        top.set(newTop, newStamp);
        return null;
    }

}
