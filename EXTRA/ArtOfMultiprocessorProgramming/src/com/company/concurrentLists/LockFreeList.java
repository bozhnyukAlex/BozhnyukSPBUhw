package com.company.concurrentLists;

import com.company.concurrentLists.util.AtomicNode;
import com.company.concurrentLists.util.Window;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class LockFreeList<T> {
    private AtomicNode<T> tail = new AtomicNode<T>();
    private AtomicNode<T> head = new AtomicNode<T>();

    public LockFreeList() {
        head.next = new AtomicMarkableReference<AtomicNode>(tail, false);
    }

    public Window<T> find(AtomicNode<T> head, int key) {
        AtomicNode<T> pred = null, curr = null, succ = null;
        boolean[] marked = {false};
        boolean snip;
        retry: while (true) {
            pred = head;
            curr = pred.next.getReference();
            while (true) {
                succ = curr.next.get(marked);
                while (marked[0]) {
                    snip = pred.next.compareAndSet(curr, succ, false, false); // pred.next <~> curr
                    if (!snip) {
                        continue retry;
                    }
                    curr = succ;
                    succ = curr.next.get(marked);
                }
                if (curr.key >= key) { // curr not logically deleted marked
                    return new Window<T>(pred, curr);
                }
                pred = curr;
                curr = succ;
            }
        }
    }

    public boolean add(T item) {
        int key = item.hashCode();
        while (true) {
            Window<T> window = find(head, key);
            AtomicNode<T> pred = window.pred, curr = window.curr;
            if (curr.key == key) {
                return false;
            } else {
                AtomicNode<T> node = new AtomicNode<T>(item);
                node.next = new AtomicMarkableReference<>(curr, false);
                if (pred.next.compareAndSet(curr, node, false, false)) {
                    return true;
                }
            }
        }
    }

    public boolean remove(T item) {
        int key = item.hashCode();
        boolean snip;
        while (true) {
            Window<T> window = find(head, key);
            AtomicNode<T> pred = window.pred, curr = window.curr;
            if (curr.key != key) {
                return false;
            } else {
                AtomicNode<T> succ = curr.next.getReference();
                snip = curr.next.attemptMark(succ, true);
                if (!snip) {
                    continue;
                }
                pred.next.compareAndSet(curr, succ, false, false);
                return true;
            }
        }
    }
    public boolean contains(T item) {
        boolean[] marked = {false};
        int key = item.hashCode();
        AtomicNode<T> curr = head;
        while (curr.key < key) {
            curr = curr.next.getReference();
            AtomicNode<T> succ = curr.next.get(marked);
        }
        return (curr.key == key && !marked[0]);
    }
}
