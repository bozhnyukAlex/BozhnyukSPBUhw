package com.company;


public class PetersonLock {
    private volatile boolean[] flag = new boolean[2];
    private volatile long victim;

    public void lock() {
        long currId = Long.parseLong(Thread.currentThread().getName());
        long otherId = 1 - currId;
        victim = currId;
        flag[(int)currId] = true;
        while (flag[(int)otherId] && victim == currId) {}
    }

    public void unlock() {
        long currId = Long.parseLong(Thread.currentThread().getName());
        flag[(int)currId] = false;
    }
}
