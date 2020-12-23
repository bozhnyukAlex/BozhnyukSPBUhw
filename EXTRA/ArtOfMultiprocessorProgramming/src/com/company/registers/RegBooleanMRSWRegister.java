package com.company.registers;

public class RegBooleanMRSWRegister implements Register<Boolean> {
    ThreadLocal<Boolean> last;
    boolean s_value; // safe MRSW register
    RegBooleanMRSWRegister(int capacity) {
         last = new ThreadLocal<Boolean>() {
            protected Boolean initialValue() { return false; };
         };
    }

    @Override
    public Boolean read() {
        return s_value;
    }

    @Override
    public void write(Boolean value) {
        if (value != last.get()) {
            last.set(value);
            s_value = value;
        }
    }
}
