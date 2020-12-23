package com.company.workStealingSharing;

public interface IDEQueue<T> {
    void pushBottom(T value);
    T popTop();
    T popBottom();
    boolean isEmpty();
}
