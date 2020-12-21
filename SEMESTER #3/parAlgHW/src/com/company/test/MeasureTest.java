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

    public final int SINGLE = -1;

    private int size = 10000000;
    @Param({"-1", "1", "2", "4", "8", "16"})
    public int threadNum;

    private int[] firstNum = new int[size];
    private int[] secondNum = new int[size];

    private String test = "";

    private Pair[] pairs = new Pair[size];

    private StepPair[] stepPairs = new StepPair[size];

    @Setup
    public void preparations() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            firstNum[i] = secondNum[i] = 9;
            sb.append("()");
            pairs[i] = new Pair(i, i + 1);
            stepPairs[i] = new StepPair(30.0, (i + 1) * 10.0);
        }
        test = sb.toString();
    }

    @Benchmark
    public int[] addMeasure() throws ExecutionException, InterruptedException {
        if (threadNum == SINGLE) {
            return Adder.add(firstNum, secondNum);
        }
        return Adder.addParallel(firstNum, secondNum, threadNum);
    }

    @Benchmark
    public boolean checkerParentsMeasure() throws InterruptedException {
        if (threadNum == SINGLE) {
            return ParensBalanceChecker.check(test);
        }
        return ParensBalanceChecker.checkParallel(test, threadNum);
    }

    @Benchmark
    public int sequenceMeasure() throws InterruptedException {
        if (threadNum == SINGLE) {
            return SequenceFinder.find(pairs);
        }
        return SequenceFinder.findParallel(pairs, threadNum);
    }

    @Benchmark
    public StatePair turtleMeasure() throws InterruptedException {
        if (threadNum == SINGLE) {
            return TurtleLocator.locate(stepPairs);
        }
        return TurtleLocator.locateParallel(stepPairs, threadNum);
    }
}
