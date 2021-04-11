package com.company.test;

import com.company.app.Info;
import com.company.app.ProblemSolver;
import com.company.concurrent.LazyList;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
public class MeasureTest {
    @Param({"1", "2", "4", "8", "16"})
    public int threadNum;

    private static final String PATH = "test.txt";

    @Benchmark
    public LazyList<Info> /*ConcurrentSkipListSet<Info>*/ solverMeasure() throws IOException, InterruptedException {
        return ProblemSolver.solve(PATH, threadNum);
    }
}
