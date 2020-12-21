package com.company.scanner;

import java.util.ArrayList;
import java.util.function.BinaryOperator;

public class ParallelScanThread<T> extends Thread {
    private final int threadNum;
    private final int tId;
    private final int fromInc;
    private final int toExc;
    private T total;
    private BinaryOperator<T> operator;
    private T idElement;
    private T[] array;
    private ArrayList<ArrayList<Info<T>>> infoCollect;

    public ParallelScanThread(int threadNum, int tId, BinaryOperator<T> operator, T[] array, ArrayList<ArrayList<Info<T>>> infoCollect) {
        this.threadNum = threadNum;
        this.tId = tId;
        this.operator = operator;
        this.infoCollect = infoCollect;
        this.array = array;
        fromInc = (array.length / threadNum) * tId;
        if (tId != threadNum - 1) {
            toExc = (array.length / threadNum) * (tId + 1);
        }
        else {
            toExc = array.length;
        }
    }

    public void send(int toId, T x) {
        Info<T> info = infoCollect.get(toId).get(tId);
        synchronized (info) {
            info.setData(x);
            info.setReady(true);
            info.notify();
        }
    }

    public T receive (int fromId) {
        Info<T> info = infoCollect.get(tId).get(fromId);
        synchronized (info) {
            while (!info.isReady()) {
                try {
                    info.wait();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return info.getData();
    }


    public T getTotal() {
        return total;
    }

    @Override
    public void run() {
        total = array[fromInc];
        for (int i = fromInc + 1; i < toExc; i++) {
            total = operator.apply(total, array[i]);
        }
        for (int k = 1; k < threadNum; k *= 2) {
            if ((tId & k) != 0) {
                send(tId - k, total);
                break;
            }
            else if (tId + k < threadNum) {
                total = operator.apply(total, receive(tId + k));
            }
        }
    }
}
