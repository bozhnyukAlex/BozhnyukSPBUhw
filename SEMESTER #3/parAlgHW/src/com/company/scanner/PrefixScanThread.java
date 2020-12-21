package com.company.scanner;

import java.util.ArrayList;
import java.util.function.BinaryOperator;

public class PrefixScanThread<T> extends Thread {
    private final int threadNum;
    private final int tId;
    private final int fromInc;
    private final int toExc;
    private T total;
    private BinaryOperator <T> operator;
    private T idElement;
  //  private Info<T>[][] infoCollect;
  //  private Info<T>[][] infoDistribute;
    private T[] array;
    private T[] result;
    private ArrayList<ArrayList<Info<T>>> infoCollect;
    private ArrayList<ArrayList<Info<T>>> infoDistribute;

    public PrefixScanThread(int threadNum, int tId, BinaryOperator<T> operator, T idElement, T[] array, T[] result, ArrayList<ArrayList<Info<T>>> infoCollect, ArrayList<ArrayList<Info<T>>> infoDistribute) {
        this.threadNum = threadNum;
        this.tId = tId;
        this.operator = operator;
        this.idElement = idElement;
        this.infoCollect = infoCollect;
        this.infoDistribute = infoDistribute;
        this.array = array;
        this.result = result;
        fromInc = (array.length / threadNum) * tId;
        if (tId != threadNum - 1) {
            toExc = (array.length / threadNum) * (tId + 1);
        }
        else {
            toExc = array.length;
        }
    }

    public void send(int toId, T x, Phase phase) {
        Info<T> info = (phase == Phase.COLLECT) ?
                //infoCollect[tId][toId] :
                //infoDistribute[tId][toId];
                infoCollect.get(toId).get(tId) :
                infoDistribute.get(toId).get(tId);
        synchronized (info) {
            info.setData(x);
            info.setReady(true);
            info.notify();
        }
    }
    public T receive(int fromId, Phase phase) {
        Info<T> info = (phase == Phase.COLLECT) ?
                //infoCollect[tId][fromId] :
                //infoDistribute[tId][fromId];
                infoCollect.get(tId).get(fromId) :
                infoDistribute.get(tId).get(fromId);
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

    @Override
    public void run() {
        total = idElement;
        for (int i = fromInc; i < toExc; i++) {
            total = operator.apply(total, array[i]);
        }
        int k;
        for (k = 1; k < threadNum; k *= 2) {
            if ((tId & k) == 0) {
                send(tId + k, total, Phase.COLLECT);
                break;
            }
            else {
                total = operator.apply(total, receive(tId - k, Phase.COLLECT));
            }
        }

        if (tId == threadNum - 1) {
            total = idElement;
        }
        if (k >= threadNum) {
            k /= 2;
        }
        while (k > 0) {
            if ((tId & k) == 0) {
                send(tId + k, total, Phase.DISTRIBUTE);
                total = receive(tId + k, Phase.DISTRIBUTE);
            }
            else {
                T temp = receive(tId - k, Phase.DISTRIBUTE);
                send(tId - k, total, Phase.DISTRIBUTE);
                total = operator.apply(temp, total);
            }
            k /= 2;
        }

        for (int i = fromInc; i < toExc; i++) {
            total = operator.apply(total, array[i]);
            result[i] = total;
        }
    }


}
