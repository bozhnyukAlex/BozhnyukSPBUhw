package com.company.test;


import com.company.app.Polynomial;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PolynomialTest {

    @Test
    void equalsTest() {
        ArrayList<Integer> first = new ArrayList<>();
        first.add(1); first.add(1); first.add(1);
        ArrayList<Integer> second = new ArrayList<>();
        second.add(1); second.add(1); second.add(1);
        ArrayList<Integer> third = new ArrayList<>();
        third.add(1); third.add(0); third.add(1);
        ArrayList<Integer> fourth = new ArrayList<>();
        fourth.add(2); fourth.add(0);
        Polynomial p1 = new Polynomial(first), p2 = new Polynomial(second),
                p3 = new Polynomial(third), p4 = new Polynomial(fourth);
        assertEquals(p1, p2);
        assertNotEquals(p1, p3);
        assertNotEquals(p1, p4);
        assertNotEquals(p3, p4);
    }
}