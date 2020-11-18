package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.BinaryOperator;

public class ReduceScanner <T> {
    private ArrayList<T> array;
    private BinaryOperator<T> operator;
    private ArrayList<T> buffer;
    private T ident;

    public ReduceScanner(ArrayList<T> array, BinaryOperator<T> operator, T ident) {
        this.array = array;
        if (array.size() % 2 == 1) {
            array.add(ident);
        }
        this.buffer = new ArrayList<>(array);
        this.ident = ident;

        this.operator = operator;
    }

    public void setArray(ArrayList<T> array) {
        this.array = array;
    }

    public ArrayList<T> getBuffer() {
        return buffer;
    }

    public void parallelScan(int threadsNum) throws ExecutionException, InterruptedException {
        ArrayList<Pair<Future<T>, Integer>> futurePairs = new ArrayList<>();
        ExecutorService es = Executors.newFixedThreadPool(threadsNum);
        for (int i = 0, k = 0; i < array.size(); i += 2, k++) {
            int copy = i;
            //futurePairs.add(es.submit(() -> operator.apply(array.get(copy), array.get(copy + 1))));
            futurePairs.add(new Pair<>(es.submit(() -> operator.apply(array.get(copy), array.get(copy + 1))), k));
        }
        int factor = 2;
        while (futurePairs.size() != 1) {
            for (int i = 0; i < futurePairs.size(); i++) {
                if (i == futurePairs.size() - 1) {
                    T x = futurePairs.get(i).getLeft().get();
                    buffer.set(buffer.size() - 1, x);
                    futurePairs.set(i, new Pair<>(futurePairs.get(i).getLeft(), i));
                    continue;
                }
                T x = futurePairs.get(i).getLeft().get();
                T y = futurePairs.get(i + 1).getLeft().get();
                int index1 = factor * (futurePairs.get(i).getRight() + 1) - 1,
                    index2 = factor * (futurePairs.get(i + 1).getRight() + 1) - 1;
                if (index2 >= buffer.size()) {
                    index2 = buffer.size() - 1;
                }
                buffer.set(index1, x);
                buffer.set(index2, y);
                futurePairs.set(i, new Pair<>(es.submit(() -> operator.apply(x, y)), i));
                futurePairs.remove(i + 1);
            }
            factor *= 2;
        }
        if (array.size() % 2 == 1) {
            buffer.set(buffer.size() - 2, futurePairs.get(0).getLeft().get());
            buffer.remove(buffer.size() - 1);
        }
        else {
            buffer.set(buffer.size() - 1, futurePairs.get(0).getLeft().get());
        }
        es.shutdown();
    }

}

class OpApplier <T> implements Callable<T> {
    private T left;
    private T right;
    private BinaryOperator<T> operator;

    public OpApplier (T left, T right) {
        this.left = left;
        this.right = right;
    }


    @Override
    public T call() throws Exception {
        return operator.apply(left, right);
    }
}
