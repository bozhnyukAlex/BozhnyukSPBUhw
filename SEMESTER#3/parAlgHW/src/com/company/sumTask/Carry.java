package com.company.sumTask;

public enum Carry {
    CARRY, MAYBE, NEVER;

    public static Carry operator (Carry a, Carry b) {
        if (b == CARRY || b == NEVER) {
            return b;
        }
        else if (b == MAYBE) {
            return a;
        }
        else {
            throw new IllegalArgumentException();
        }
    }
}
