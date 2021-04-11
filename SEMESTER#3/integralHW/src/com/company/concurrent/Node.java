package com.company.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Node <T>{
    public T value;
    public int key;
    public Node<T> next;
    public Lock lock = new ReentrantLock();
    public volatile boolean marked = false;

    public Node(int key) {
        this.key = key;
    }

    public Node(T value) {
        this.value = value;
        this.key = value.hashCode();
    }

    public T getValue() {
        return value;
    }

    public int getHashCode() {
        return key;
    }
}
