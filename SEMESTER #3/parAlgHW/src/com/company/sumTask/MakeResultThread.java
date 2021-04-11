package com.company.sumTask;

public class MakeResultThread extends Thread {
    private int[] firstMore;
    private int[] secondLess;
    private Carry[] carries;
    private int[] result;
    private int tId;
    private int threadNum;
    private int fromInc;
    private int toExc;

    public MakeResultThread(int[] firstMore, int[] secondLess, Carry[] carries, int[] result, int threadNum, int tId) {
        this.firstMore = firstMore;
        this.secondLess = secondLess;
        this.carries = carries;
        this.result = result;
        this.tId = tId;
        this.threadNum = threadNum;
        int size = firstMore.length;
        fromInc = (size / threadNum) * tId;
        if (tId != threadNum - 1) {
            toExc = (size / threadNum) * (tId + 1);
        }
        else {
            toExc = size;
        }
    }

    @Override
    public void run() {
        int carryInt = 0;
        for (int i = fromInc; i < toExc; i++) {
            if (i == 0) {
                carryInt = 0;
            }
            else {
                carryInt = (carries[i - 1] == Carry.CARRY)? 1 : 0;
            }
            if (i <= secondLess.length - 1) {
                int res = (firstMore[i] + secondLess[i] + carryInt) % 10;
                result[i] = res;
            }
            else {
                int res = (firstMore[i] + carryInt) % 10;
                result[i] = res;
            }
        }
    }
}
