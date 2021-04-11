package com.company.app;

import java.util.ArrayList;
import java.util.function.Function;

public class Polynomial {
    public ArrayList<Integer> coefficients;
    public Function<Double, Double> function;

    public Polynomial(ArrayList<Integer> coefficients) {
        this.coefficients = coefficients;
        this.function = (x -> {
            double pow = coefficients.size() - 1;
            double res = 0;
            for (Integer coefficient : coefficients) {
                res += coefficient * Math.pow(x, pow);
                pow--;
            }
            return res;
        });
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Polynomial)){
            return false;
        }
        Polynomial pol = (Polynomial) obj;
        return pol.coefficients.equals(this.coefficients);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        int pow = coefficients.size() - 1;
        for (int i = 0; i < coefficients.size(); i++) {
           int cur = coefficients.get(i);
           if (pow == 0) {
               if (cur > 0) {
                   sb.append("+");
               }
               sb.append(cur);
               continue;
           }
           if (cur < 0) {
               sb.append(cur);
               sb.append("x").append((pow == 1) ? "" : ("^" + pow));
               pow--;
           }
           else if (cur > 0) {
               if (i != 0) {
                   sb.append("+");
               }
               if (cur != 1) {
                   sb.append(cur);
               }
               sb.append("x").append((pow == 1) ? "" : ("^" + pow));
               pow--;
           }
           else {
               pow--;
           }
        }
        return sb.toString();
    }
}
