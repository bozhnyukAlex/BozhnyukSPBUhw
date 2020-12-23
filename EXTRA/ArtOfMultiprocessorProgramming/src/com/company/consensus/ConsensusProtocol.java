package com.company.consensus;

public abstract class ConsensusProtocol<T> implements Consensus<T> {
    public static int THREAD_NUM = 42;
    protected T[] proposed = (T[]) new Object[THREAD_NUM];
    void propose(T value) {
        proposed[(int) Thread.currentThread().getId()] = value;
    }

    abstract public T decide(T value);
}
