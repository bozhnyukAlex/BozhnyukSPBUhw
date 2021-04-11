package com.company.sequenceTask;

public class Pair {
    public int left;
    public int right;

    public Pair(int left, int right) {
        this.left = left;
        this.right = right;
    }

    public static Pair operator (Pair a, Pair b) {
        return new Pair(a.left * b.left, a.right * b.left + b.right);
    }

}
