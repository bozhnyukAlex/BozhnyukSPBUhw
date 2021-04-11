package com.company.scanner;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.BinaryOperator;

public class ParallelScanner <T>{
    private T[] array;
    private T total;
    private BinaryOperator<T> operator;
    public ParallelScanner(T[] array, T total, BinaryOperator<T> operator) {
        this.array = array;
        this.operator = operator;
        this.total = total;
    }

    public T getTotal() {
        return total;
    }

    public void scan(int threadNum) throws InterruptedException {
        ArrayList<ArrayList<Info<T>>> infoCollect = new ArrayList<>();
        for (int i = 0; i < threadNum; i++) {
            ArrayList<Info<T>> infoCol = new ArrayList<>();
            for (int j = 0; j < threadNum; j++) {
                infoCol.add(new Info<T>());
            }
            infoCollect.add(infoCol);
        }
        ArrayList<ParallelScanThread<T>> threadList = new ArrayList<>();
        for (int i = 0; i < threadNum; i++) {
            ParallelScanThread<T> thread = new ParallelScanThread<T>(threadNum, i, operator, array, infoCollect);
            threadList.add(thread);
            thread.start();
        }

        for (int i = 0; i < threadNum; i++) {
            threadList.get(i).join();
        }
        total = threadList.get(0).getTotal();
    }
}
