package com.company.test;

import com.company.parensTask.ParensBalanceChecker;
import com.company.sequenceTask.Pair;
import com.company.sequenceTask.SequenceFinder;
import com.company.sumTask.Adder;
import com.company.turtleTask.StatePair;
import com.company.turtleTask.StepPair;
import com.company.turtleTask.TurtleLocator;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
public class MeasureTest {

    private int size = 10000000;
    @Param({"2", "4", "8", "16"})
    public int threadNum;

    private int[] firstNum = new int[size];
    private int[] secondNum = new int[size];

    private String test = "";

    private Pair[] pairs = new Pair[size];

    private StepPair[] stepPairs = new StepPair[size];

    @Setup
    public void preparations() {
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < size; i++) {
            firstNum[i] = secondNum[i] = 9;
            sb.append("()");
            pairs[i] = new Pair(i, i + 1);
            stepPairs[i] = new StepPair(30.0, (i + 1) * 10.0);
        }
        test = sb.toString();
    }

    @Benchmark
    public int[] addMeasureSingle() {
        return Adder.add(firstNum, secondNum);
    }

    @Benchmark
    public int[] addMeasureParallel() throws ExecutionException, InterruptedException {
        return Adder.addParallel(firstNum, secondNum, threadNum);
    }

    @Benchmark
    public boolean checkerParentsMeasureSingle() {
        return ParensBalanceChecker.check(test);
    }

    @Benchmark
    public boolean checkerParentsMeasureParallel() throws InterruptedException {
        return ParensBalanceChecker.checkParallel(test, threadNum);
    }

    @Benchmark
    public int sequenceMeasureSingle() {
        return SequenceFinder.find(pairs);
    }

    @Benchmark
    public int sequenceMeasureParallel() throws InterruptedException {
        return SequenceFinder.findParallel(pairs, threadNum);
    }

    @Benchmark
    public StatePair turtleMeasureSingle() {
        return TurtleLocator.locate(stepPairs);
    }

    @Benchmark
    public StatePair turtleMeasureParallel() throws InterruptedException {
        return TurtleLocator.locateParallel(stepPairs, threadNum);
    }
}
