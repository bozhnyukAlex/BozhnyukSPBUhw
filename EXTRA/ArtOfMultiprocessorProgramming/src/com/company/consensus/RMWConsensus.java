package com.company.consensus;
/*

public class RMWConsensus extends ConsensusProtocol {
    // initialize to v such that f(v) != v
    private RMWRegister r = new RMWRegister(v);
    @Override
    public Object decide(Object value) {
        propose(value);
        int i = (int) Thread.currentThread().getId(); // my index
        int j = 1-i; // other’s index
        if (r.rmw() == v) {// I’m first, I win
            return proposed[i];
        }
        else {// I’m second, I lose
            return proposed[j];
        }

    }
}
*/
