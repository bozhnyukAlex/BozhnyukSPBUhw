package com.company.workStealingSharing;

import java.util.concurrent.RecursiveAction;

public class CircularArray {
    private int logCapacity;
    private RecursiveAction[] currentTasks;
    CircularArray(int logCapacity) {
        this.logCapacity = logCapacity;
        currentTasks = new RecursiveAction[1 << logCapacity];
    }
    int capacity() {
        return 1 << logCapacity;
    }
    RecursiveAction get(int i) {
        return currentTasks[i % capacity()];
    }
    void put(int i, RecursiveAction task) {
        currentTasks[i % capacity()] = task;
    }
    CircularArray resize(int bottom, int top) {
        CircularArray newTasks =
                new CircularArray(logCapacity+1);
        for (int i = top; i < bottom; i++) {
            newTasks.put(i, get(i));
        }
        return newTasks;
    }
}
