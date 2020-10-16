package com.company;

public class CounterHandler {
    private int cnt;
    public CounterHandler(int cnt) {
        this.cnt = cnt;
    }

    public int getCnt() {
        return cnt;
    }

    public void inc() {
        cnt++;
    }

    public void dec() {
        cnt--;
    }
}
