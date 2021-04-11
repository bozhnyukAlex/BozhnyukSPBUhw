package com.company.scanner;

public class Info <T> {
    private T data;
    private boolean isReady;

    public Info() {
        isReady = false;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }
}
