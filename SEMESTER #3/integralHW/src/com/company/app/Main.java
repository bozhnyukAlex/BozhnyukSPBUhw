package com.company.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        ArrayList<Future<Info>> futureList = new ArrayList<>(ProblemSolver.solve("test.txt", 4));
        for(Future<Info> future : futureList) {
            System.out.println(future.get());
        }
    }
}
