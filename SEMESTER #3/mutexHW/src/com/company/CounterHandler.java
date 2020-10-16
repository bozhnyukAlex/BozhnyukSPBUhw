package com.company;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CounterHandler {
    private int cnt;
    private Lock mutex;
    public CounterHandler(int cnt) {
        this.cnt = cnt;
        mutex = new ReentrantLock();
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
