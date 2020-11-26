package com.company.app;

public class Info implements Comparable<Info>{
    private Polynomial polynomial;
    private double from;
    private double to;
    private double result;

    public Info(Polynomial polynomial, double from, double to) {
        this.polynomial = polynomial;
        this.from = from;
        this.to = to;
        this.result = -1;
    }

    public Polynomial getPolynomial() {
        return polynomial;
    }

    public double getFrom() {
        return from;
    }

    public double getTo() {
        return to;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return polynomial.toString() + " " + from + " " + to + " " + result;
    }

    @Override
    public int compareTo(Info info) {
        return this.polynomial.coefficients.size() - info.polynomial.coefficients.size();
    }
}
