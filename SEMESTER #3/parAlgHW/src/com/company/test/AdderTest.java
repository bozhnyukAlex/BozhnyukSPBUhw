package com.company.test;

import com.company.sumTask.Adder;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
public class AdderTest {
    @Test
    public void check() {
        assertArrayEquals(Adder.add(new int[] {9, 7, 5, 3, 1},
                                    new int[] {3, 2, 1, 8, 6}),
                                    new int[] {2, 0, 7, 1, 8, 0});

        assertArrayEquals(Adder.add(new int[] {9, 9 ,9 ,9 ,9},
                                    new int[] {9, 9, 9, 9, 9}),
                                    new int[] {8, 9, 9, 9, 9, 1});
    }

    @Test
    public void checkParallel() throws ExecutionException, InterruptedException {
        assertArrayEquals(Adder.addParallel(new int[] {9, 7, 5, 3, 1},
                new int[] {3, 2, 1, 8, 6}, 2),
                new int[] {2, 0, 7, 1, 8, 0});

        assertArrayEquals(Adder.addParallel(new int[] {9, 9 ,9 ,9 ,9},
                new int[] {9, 9, 9, 9, 9}, 2),
                new int[] {8, 9, 9, 9, 9, 1});
    }
}
