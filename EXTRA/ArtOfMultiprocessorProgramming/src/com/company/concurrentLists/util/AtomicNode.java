package com.company.concurrentLists.util;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class AtomicNode<T> {
    public T item;
    public int key;
    public AtomicMarkableReference<AtomicNode> next = new AtomicMarkableReference<AtomicNode>(null, false);

    public AtomicNode(T item) {
        this.item = item;
    }

    public AtomicNode(int key) {
        this.key = key;
    }
    public AtomicNode() { }
}
