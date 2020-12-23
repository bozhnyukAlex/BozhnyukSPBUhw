package com.company.registers;

public class SafeBooleanMRSWRegister implements Register <Boolean>{
    boolean[] s_table; // array of safe SRSW registers
    public SafeBooleanMRSWRegister(int capacity) {
        s_table = new boolean[capacity];
    }

    @Override
    public Boolean read() {
        return s_table[(int) Thread.currentThread().getId()];
    }

    @Override
    public void write(Boolean value) {
        for (int i = 0; i < s_table.length; i++) {
            s_table[i] = value;
        }
    }
}
