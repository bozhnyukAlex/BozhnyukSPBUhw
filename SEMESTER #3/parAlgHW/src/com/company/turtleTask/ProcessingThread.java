package com.company.turtleTask;

import com.company.turtleTask.Task;

public class ProcessingThread extends Thread {
    private Task task;
    private int tId;
    private int threadNum;
    private int fromInc;
    private int toExc;

    private StepPair[] array;
    private Double[] angles;
    private StatePair[] states;


    public ProcessingThread(Task task, StepPair[] array, int tId, int threadNum) {
        this.task = task;
        this.tId = tId;
        this.threadNum = threadNum;
        this.array = array;

        int size = array.length;
        fromInc = (size / threadNum) * tId;
        if (tId != threadNum - 1) {
            toExc = (size / threadNum) * (tId + 1);
        }
        else {
            toExc = size;
        }
    }

    public void setAngles(Double[] angles) {
        this.angles = angles;
    }

    public void setStates(StatePair[] states) {
        this.states = states;
    }

    @Override
    public void run() {
        switch (task) {
            case CONVERT_TO_ANGLES: {
                for (int i = fromInc; i < toExc; i++) {
                    angles[i] = Math.toRadians(array[i].angleStep);
                }
                break;
            }
            case MAKE_STATE_PAIRS_ARRAY: {
                for (int i = fromInc; i < toExc; i++) {
                    states[i] = new StatePair(angles[i], array[i].distanceStep);
                }
                break;
            }
            default: {
                throw new IllegalArgumentException("Wrong Task!");
            }
        }
    }
}
