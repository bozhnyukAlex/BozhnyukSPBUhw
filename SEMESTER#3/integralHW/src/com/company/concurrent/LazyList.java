package com.company.concurrent;

import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public class LazyList<T> implements Iterable<T>{
    private Node<T> tail = new Node<T>(Integer.MAX_VALUE);
    private Node<T> head = new Node<T>(Integer.MIN_VALUE);
    private AtomicInteger size = new AtomicInteger(0);

    public LazyList() {
        head.next = tail;
    }

    private boolean validate(Node<T> pred, Node<T> curr) {
        return !pred.marked && !curr.marked && pred.next == curr;
    }

    public boolean add(T item) {
        int key = item.hashCode();
        while (true) {
            Node<T> pred = head;
            Node<T> curr = head.next;
            while (curr.key < key) {
                pred = curr;
                curr = curr.next;
            }
            pred.lock.lock();
            try {
                curr.lock.lock();
                try {
                    if (validate(pred, curr)) {
                        if (curr.key == key) { // у текущего >=, у предыдущего <
                            return false;
                        }
                        else {
                            Node<T> node = new Node<T>(item);
                            node.next = curr;
                            pred.next = node;
                            size.getAndIncrement();
                        }
                    }
                }
                finally {
                    curr.lock.unlock();
                }
            }
            finally {
                pred.lock.unlock();
            }
        }
    }

    public boolean remove(T item) {
        int key = item.hashCode();
        while (true) {
            Node<T> pred = head;
            Node<T> curr = head.next;
            while (curr.key < key) {
                pred = curr;
                curr = curr.next;
            }
            pred.lock.lock();
            try {
                curr.lock.lock();
                try {
                    if (validate(pred, curr)) {
                        if (curr.key != key) {
                            return false;
                        }
                        else {
                            curr.marked = true; // удалили логически
                            pred.next = curr.next; // а теперь физически
                            size.getAndDecrement();
                            return true;
                        }
                    }
                }
                finally {
                    curr.lock.unlock();
                }
            }
            finally {
                pred.lock.unlock();
            }
        }
    }

    public boolean contains(T item) {
        int key = item.hashCode();
        Node<T> curr = head;
        while (curr.key < key) {
            curr = curr.next;
        }
        return curr.key == key && !curr.marked;
    }

    public Node<T> getHead() {
        return head;
    }

    public boolean isEmpty() {
        return size.get() == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new LazyIterator<>(this);
    }
}
