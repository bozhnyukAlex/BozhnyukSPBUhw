package com.company.consensus;

public interface Consensus<T> {
    T decide(T value);
}
