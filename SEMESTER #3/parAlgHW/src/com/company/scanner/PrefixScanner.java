package com.company.scanner;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.function.BinaryOperator;

public class PrefixScanner <T> {
    private T[] array;
    private T[] result;
    private BinaryOperator<T> operator;
    private T idElem;

    public PrefixScanner(T[] array, T[] result, BinaryOperator<T> operator, T idElem) {
        this.idElem = idElem;
        this.array = array;
        this.operator = operator;
        this.result = result;
    }

    public void scan(int threadNum) throws InterruptedException {
   //     Info<T>[][] infoCollect = new Info[threadNum][threadNum];
   //     Info<T>[][] infoDistribute = new Info[threadNum][threadNum];
        ArrayList<ArrayList<Info<T>>> infoCollect = new ArrayList<>();
        ArrayList<ArrayList<Info<T>>> infoDistribute = new ArrayList<>();
        for (int i = 0; i < threadNum; i++) {
            /*for (int j = 0; j < threadNum; j++) {
                infoCollect[i][j] = new Info<>();
                infoDistribute[i][j] = new Info<>();
            }*/
            ArrayList<Info<T>> infoCol = new ArrayList<>();
            ArrayList<Info<T>> infoDist = new ArrayList<>();
            for (int j = 0; j < threadNum; j++) {
                infoCol.add(new Info<T>());
                infoDist.add(new Info<T>());
            }
            infoCollect.add(infoCol);
            infoDistribute.add(infoDist);
        }
        ArrayList<PrefixScanThread<T>> threadList = new ArrayList<>();
        for (int i = 0; i < threadNum; i++) {
            PrefixScanThread<T> thread = new PrefixScanThread<T>(threadNum, i, operator, idElem, array, result, infoCollect, infoDistribute);
            threadList.add(thread);
            thread.start();
        }

        for (int i = 0; i < threadNum; i++) {
            threadList.get(i).join();
        }
    }
}

