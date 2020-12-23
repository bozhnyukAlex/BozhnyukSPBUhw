package com.company.consensus;

import java.util.concurrent.atomic.AtomicInteger;

public class CASConsensus extends ConsensusProtocol {
    private final int FIRST = -1;
    private AtomicInteger r = new AtomicInteger(FIRST);

    @Override
    public Object decide(Object value) {
        propose(value);
        int i = (int) Thread.currentThread().getId();
        if (r.compareAndSet(FIRST, i)) { // I won
            return proposed[i];
        }
        else { // I lost
            return proposed[r.get()];
        }
    }
}
