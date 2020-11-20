package com.company.sumTask;

import com.company.scanner.PrefixScanner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Adder {
    public static int[] add(int[] first, int[] second) {
        int[] res = new int[Math.max(first.length, second.length) + 1];
        int carry = 0;
        int[] more, less;
        if (first.length >= second.length) {
            more = first;
            less = second;
        }
        else {
            more = second;
            less = first;
        }
        for (int i = 0; i < res.length - 1; i++) {
            if (i <= less.length - 1) {
                res[i] = (less[i] + more[i] + carry) % 10;
                carry = (less[i] + more[i] + carry) / 10;
            }
            else {
                res[i] = (more[i] + carry) % 10;
                carry = (more[i] + carry) / 10;
            }
        }
        res[res.length - 1] = carry;
        return res;
    }

    public static int[] parallelAdd(int[] first, int[] second, int threadNum) throws InterruptedException, ExecutionException {
        int[] res = new int[Math.max(first.length, second.length) + 1];
        int[] more, less;
        if (first.length >= second.length) {
            more = first;
            less = second;
        }
        else {
            more = second;
            less = first;
        }
        ArrayList<PrepareCarriesThread> threads = new ArrayList<>();
        Carry[] carries = new Carry[more.length];
        for (int i = 0; i < threadNum; i++) {
            PrepareCarriesThread thread = new PrepareCarriesThread(more, less, carries, threadNum, i);
            threads.add(thread);
            thread.start();
        }
        for (int i = 0; i < threadNum; i++) {
            threads.get(i).join();
        }

        PrefixScanner<Carry> carryPrefixScanner = new PrefixScanner<>(carries, carries, Carry::operator, Carry.MAYBE);
        carryPrefixScanner.scan(threadNum);
        ArrayList<MakeResultThread> threadsCalc = new ArrayList<>();
        for (int i = 0; i < threadNum; i++) {
            MakeResultThread thread = new MakeResultThread(more, less, carries, res, threadNum, i);
            threadsCalc.add(thread);
            thread.start();
        }
        for (int i = 0; i < threadNum; i++) {
            threadsCalc.get(i).join();
        }
        res[res.length - 1] = (carries[carries.length - 1] == Carry.CARRY)? 1 : 0;
        return res;
    }


}
