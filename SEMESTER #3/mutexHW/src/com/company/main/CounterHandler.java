package com.company.main;

import com.company.locks.AbstractLock;

public class CounterHandler {
    private int cnt;
    private AbstractLock mutex;
    public CounterHandler(int cnt, AbstractLock lock) {
        this.cnt = cnt;
        mutex = lock;
    }

    public int getCnt() {
        return cnt;
    }

    public void inc() {
        mutex.lock();
        try {
            cnt++;
        } finally {
            mutex.unlock();
        }
    }

    public void dec() {
        mutex.lock();
        try {
            cnt--;
        } finally {
            mutex.unlock();
        }
    }
}
