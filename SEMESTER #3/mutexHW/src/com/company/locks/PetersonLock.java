package com.company.locks;


public class PetersonLock extends AbstractLock{
    private volatile boolean[] flag = new boolean[2];
    private volatile long victim;

    @Override
    public void lock() {
        long currId = Long.parseLong(Thread.currentThread().getName());
        long otherId = 1 - currId;
        victim = currId;
        flag[(int)currId] = true;
        while (flag[(int)otherId] && victim == currId) {}
    }

    @Override
    public void unlock() {
        long currId = Long.parseLong(Thread.currentThread().getName());
        flag[(int)currId] = false;
    }
}
