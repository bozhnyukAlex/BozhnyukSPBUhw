package com.company.sumTask;

public class PrepareCarriesThread extends Thread {
    private int[] firstMore;
    private int[] secondLess;
    private Carry[] carries;
    private int fromInc;
    private int toExc;
    private int threadNum;
    private int tId;
    public PrepareCarriesThread(int[] fMore, int[] sLess, Carry[] carries ,  int threadNum, int tId) {
        this.firstMore = fMore;
        this.secondLess = sLess;
        this.threadNum = threadNum;
        this.tId = tId;

        int size = fMore.length;
        fromInc = (size / threadNum) * tId;
        if (tId != threadNum - 1) {
            toExc = (size / threadNum) * (tId + 1);
        }
        else {
            toExc = size;
        }
        this.carries = carries;
    }

    public Carry[] getCarries() {
        return carries;
    }

    @Override
    public void run() {
        for (int i = fromInc; i < toExc; i++) {
            if (i <= secondLess.length - 1) {
                int res = firstMore[i] + secondLess[i];
                if (res >= 10) {
                    carries[i] = Carry.CARRY;
                }
                else if (res == 9) {
                    carries[i] = Carry.MAYBE;
                }
                else {
                    carries[i] = Carry.NEVER;
                }
            }
            else {
                carries[i] = Carry.NEVER;
            }
        }
    }
}
