package com.company.test;

import com.company.app.Info;
import com.company.app.Polynomial;
import com.company.app.Task;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentSkipListSet;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void parseTest() {
        String input1 = "(1,2,3) 4 5";
        ArrayList<Integer> pol = new ArrayList<>();
        pol.add(1); pol.add(2); pol.add(3);
        Info info1 = new Info(new Polynomial(pol), 4, 5);
        assertEquals(new Task(input1, new ConcurrentSkipListSet<>()).parse(), info1);
    }

    @Test
    void calculateTest() {
        ArrayList<Integer> pol = new ArrayList<>();
        pol.add(1); pol.add(2);
        assertTrue(compareDouble(new Task("(1,2) 4 10", new ConcurrentSkipListSet<>()).calculate(4, 10, new Polynomial(pol)), 54.0));
    }

    boolean compareDouble(double a, double b) {
        double c = a - b;
        return Math.abs(c) <= 0.000001;
    }
}