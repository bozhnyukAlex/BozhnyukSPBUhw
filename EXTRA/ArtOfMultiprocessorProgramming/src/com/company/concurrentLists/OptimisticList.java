package com.company.concurrentLists;

import com.company.concurrentLists.util.Node;

public class OptimisticList<T> {

    private Node<T> tail = new Node<T>(Integer.MAX_VALUE);
    private Node<T> head = new Node<T>(Integer.MIN_VALUE);

    public OptimisticList() {
        head.next = tail;
    }
    private boolean validate(Node<T> pred, Node<T> curr) {
        Node<T> node = head;
        while (node.key <= pred.key) {
            if (node == pred) {
                return pred.next == curr;
            }
            node = node.next;
        }
        return false;
    }

    public boolean add(T item) {
        int key = item.hashCode();
        while (true) {
            Node<T> pred = head;
            Node<T> curr = pred.next;
            while (curr.key <= key) {
                pred = curr;
                curr = curr.next;
            }
            pred.lock();
            curr.lock();
            try {
                if (validate(pred, curr)) {
                    if (curr.key == key) {
                        return false;
                    } else {
                        Node<T> node = new Node<T>(item);
                        node.next = curr;
                        pred.next = node;
                        return true;
                    }
                }
            } finally {
                pred.unlock();
                curr.unlock();
            }
        }
    }

    public boolean remove(T item) {
        int key = item.hashCode();
        while (true) {
            Node<T> pred = head;
            Node<T> curr = pred.next;
            while (curr.key < key) {
                pred = curr; curr = curr.next;
            }
            pred.lock(); curr.lock();
            try {
                if (validate(pred, curr)) {
                    if (curr.key == key) {
                        pred.next = curr.next;
                        return true;
                    } else {
                        return false;
                    }
                }
            } finally {
                pred.unlock(); curr.unlock();
            }
        }
    }

    public boolean contains(T item) {
        int key = item.hashCode();
        while (true) {
            Node<T> pred = this.head;
            Node<T> curr = pred.next;
            while (curr.key < key) {
                pred = curr; curr = curr.next;
            }
            try {
                pred.lock();
                curr.lock();
                if (validate(pred, curr)) {
                    return (curr.key == key);
                }
            } finally { // always unlock
                pred.unlock();
                curr.unlock();
            }
        }
    }

}
