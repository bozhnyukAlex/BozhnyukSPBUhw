package com.company.concurrentLists.util;

public class Window <T> {
    public AtomicNode<T> pred, curr;
    public Window(AtomicNode<T> myPred, AtomicNode<T> myCurr) {
        pred = myPred;
        curr = myCurr;
    }
}
