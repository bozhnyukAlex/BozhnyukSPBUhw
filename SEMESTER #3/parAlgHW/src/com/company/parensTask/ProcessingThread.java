package com.company.parensTask;

public class ProcessingThread extends Thread {
    private Task task;
    private String string;
    private Integer[] array;
    private int tId;
    private int threadNum;
    private int fromInc;
    private int toExc;
    private boolean arePrefixesCorrect;
    private Integer[] prefixes;

    public ProcessingThread(Task task, String string, Integer[] array, int tId, int threadNum) {
        this.task = task;
        this.string = string;
        this.array = array;
        this.tId = tId;
        this.threadNum = threadNum;

        int size = string.length();
        fromInc = (size / threadNum) * tId;
        if (tId != threadNum - 1) {
            toExc = (size / threadNum) * (tId + 1);
        }
        else {
            toExc = size;
        }
        arePrefixesCorrect = true;
    }

    public void setPrefixes(Integer[] prefixes) {
        this.prefixes = prefixes;
    }

    public boolean arePrefixesCorrect() {
        return arePrefixesCorrect;
    }

    @Override
    public void run() {
        switch (task) {
            case CHECK_PREFIXES: {
                for (int i = fromInc; i < toExc; i++) {
                    if (prefixes[i] < 0) {
                        arePrefixesCorrect = false;
                        return;
                    }
                }
                break;
            }
            case CONVERT_TO_INT: {
                for (int i = fromInc; i < toExc; i++) {
                    if (string.charAt(i) == ')') {
                        array[i] = -1;
                    }
                    else if (string.charAt(i) == '(') {
                        array[i] = 1;
                    }
                    else {
                        throw new IllegalArgumentException("Wrong Input!\n");
                    }
                }
                break;
            }
            default: {
                throw new IllegalArgumentException("Wrong task!");
            }
        }
    }
}
