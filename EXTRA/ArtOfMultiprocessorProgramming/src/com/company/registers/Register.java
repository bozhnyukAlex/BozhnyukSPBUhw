package com.company.registers;

public interface Register<T> {
    T read();
    void write(T value);
}
