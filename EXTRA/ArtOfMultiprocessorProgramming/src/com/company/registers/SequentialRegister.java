package com.company.registers;

public class SequentialRegister <T> implements Register<T>{
    private T value;
    @Override
    public T read() {
        return value;
    }

    @Override
    public void write(T value) {
        this.value = value;
    }
}
