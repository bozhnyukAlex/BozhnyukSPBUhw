package com.company.turtleTask;

import com.company.scanner.ParallelScanner;
import com.company.scanner.PrefixScanner;
import com.company.sequenceTask.Pair;

import java.util.ArrayList;
import java.util.Arrays;

public class TurtleLocator {

    public static StatePair locate(StepPair[] array) {
        double[] angles = Arrays.stream(array).mapToDouble(p -> Math.toRadians(p.angleStep)).toArray();
        double totalAngle = angles[0];
        StatePair[] statePairs = new StatePair[angles.length];
        statePairs[0] = new StatePair(angles[0], array[0].distanceStep);
        for (int i = 1; i < angles.length; i++) {
            totalAngle += angles[i];
            angles[i] = totalAngle;
            statePairs[i] = new StatePair(angles[i], array[i].distanceStep);
        }

        StatePair total = statePairs[0];
        for (int i = 1; i < statePairs.length; i++) {
            total = StatePair.operator(total, statePairs[i]);
        }
        return total;


    }

    public static StatePair locateParallel(StepPair[] array, int threadNum) throws InterruptedException {
        Double[] angles = new Double[array.length];
        ArrayList<ProcessingThread> threads = new ArrayList<>();
        for (int i = 0; i < threadNum; i++) {
            ProcessingThread thread = new ProcessingThread(Task.CONVERT_TO_ANGLES, array, i, threadNum);
            thread.setAngles(angles);
            threads.add(thread);
            thread.start();
        }
        for (ProcessingThread thread : threads) {
            thread.join();
        }
        threads.clear();
        (new PrefixScanner<Double>(angles, angles, Double::sum, 0.0)).scan(threadNum);
        StatePair[] statePairs = new StatePair[array.length];
        for (int i = 0; i < threadNum; i++) {
            ProcessingThread thread = new ProcessingThread(Task.MAKE_STATE_PAIRS_ARRAY, array, i, threadNum);
            thread.setAngles(angles);
            thread.setStates(statePairs);
            threads.add(thread);
            thread.start();
        }
        for (ProcessingThread thread : threads) {
            thread.join();
        }
        StatePair total = new StatePair(0.0, 0.0);
        ParallelScanner<StatePair> statesParallelScanner = new ParallelScanner<>(statePairs, total, StatePair::operator);
        statesParallelScanner.scan(threadNum);
        return statesParallelScanner.getTotal();
    }
}
