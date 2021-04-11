package com.company.app;

import com.company.concurrent.LazyList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
       ProblemSolver.solve("test.txt", 16);
    }
}
