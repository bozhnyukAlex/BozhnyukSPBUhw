package com.company.concurrentLists.util;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Node <T> {
    public T item;
    public int key;
    public Node<T> next;
    public Lock lock = new ReentrantLock();
    public volatile boolean marked = false;

    public Node(int key) {
        this.key = key;
    }

    public Node(T item) {
        this.item = item;
    }

    @Override
    public int hashCode() {
        return key;
    }

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }

}
