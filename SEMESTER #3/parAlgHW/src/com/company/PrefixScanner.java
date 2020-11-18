package com.company;

import java.util.ArrayList;
import java.util.function.BinaryOperator;

public class PrefixScanner <T> {
    private ArrayList<T> array;
    private BinaryOperator<T> operator;
    private ReduceScanner<T> reduceScanner;
    private T idElem;

    public PrefixScanner(ArrayList<T> array, BinaryOperator<T> operator, T idElem) {
        this.idElem = idElem;
        this.array = array;
        this.operator = operator;
        this.reduceScanner = new ReduceScanner<>(array, operator, idElem);
    }
}
