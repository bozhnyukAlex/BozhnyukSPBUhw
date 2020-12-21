package com.company.concurrent;

import java.util.Iterator;

public class LazyIterator<T> implements Iterator<T> {
    Node<T> curr;

    public LazyIterator(LazyList<T> list) {
        curr = list.getHead();
    }

    @Override
    public boolean hasNext() {
        return curr != null;
    }

    @Override
    public T next() {
        T val = curr.value;
        curr = curr.next;
        return val;
    }
}
