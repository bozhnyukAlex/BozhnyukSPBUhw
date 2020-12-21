package com.company.app;

import com.company.concurrent.LazyList;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentSkipListSet;

public class Task implements Runnable {
    private String input;
    //private ConcurrentSkipListSet<Info> infoSet;
    private LazyList<Info> infoSet;
    private final double stepNum = 1000000;
    private Info taskInfo;

    public Task(String input, /*ConcurrentSkipListSet<Info> infos*/ LazyList<Info> infos) {
        this.input = input;
        this.infoSet = infos;
    }

    @Override
    public void run() {
        taskInfo = parse();
        Info prev = null;
        double res;
        for (Info info : infoSet) {
            if (info == null) {
                continue;
            }
            if (info.getPolynomial().equals(taskInfo.getPolynomial())
                    && Double.compare(info.getFrom(), taskInfo.getFrom()) >= 0
                    && Double.compare(info.getTo(), taskInfo.getTo()) <= 0) {
                prev = info;
                break;
            }
        }


        if (prev != null) {
            res = calculate(taskInfo.getFrom(), prev.getFrom(), taskInfo.getPolynomial()) +
                    prev.getResult() +
                    calculate(prev.getTo(), taskInfo.getTo(), taskInfo.getPolynomial());
        }
        else {
            res = calculate(taskInfo.getFrom(), taskInfo.getTo(), taskInfo.getPolynomial());
        }
        taskInfo.setResult(res);
        infoSet.add(taskInfo);
        //System.out.println(taskInfo);
    }


    public Info parse() {
        String[] strings = input.split("\\s+");
        if (strings.length != 3) {
            throw new IllegalArgumentException("Wrong Input, should be no spaces in beginning and no other parameters!");
        }
        if (strings[0].charAt(0) != '(' || strings[0].charAt(strings[0].length() - 1) != ')') {
            throw new IllegalArgumentException("First element must be tuple!");
        }
        String[] coefficients = strings[0].substring(1, strings[0].length() - 1).split(",\\s*");
        ArrayList<Integer> numC = new ArrayList<>();
        for (String coefficient : coefficients) {
            numC.add(Integer.parseInt(coefficient));
        }
        Polynomial polynomial = new Polynomial(numC);
        double from = Double.parseDouble(strings[1]);
        double to = Double.parseDouble(strings[2]);
        return new Info(polynomial, from, to);
    }

    public Double calculate(double from, double to, Polynomial polynomial) {
        final double width = (to - from) / stepNum;

        double trapezoidal_integral = 0;
        for (int step = 0; step < stepNum; step++) {
        final double x1 = from + step*width;
        final double x2 = from + (step+1)*width;
            trapezoidal_integral += 0.5 * (x2 - x1)
                    * (polynomial.function.apply(x1) + polynomial.function.apply(x2));
        }
        return trapezoidal_integral;
    }

    public LazyList<Info> /*ConcurrentSkipListSet<Info>*/ getInfoSet() {
        return infoSet;
    }
}
