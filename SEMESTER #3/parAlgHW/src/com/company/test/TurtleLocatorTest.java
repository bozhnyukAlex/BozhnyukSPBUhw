package com.company.test;

import com.company.turtleTask.StatePair;
import com.company.turtleTask.StepPair;
import com.company.turtleTask.TurtleLocator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TurtleLocatorTest {
    @Test
    public void check() {
        assertEquals(TurtleLocator.locate(new StepPair[] {
                new StepPair(90.0, 2.0),
                new StepPair(90.0, 3.0),
                new StepPair(90.0, 2.0),
                new StepPair(0.0, 3.0)
        }), new StatePair(3.9269908169872414, 4.242640687119286));
    }

    @Test
    public void checkParallel() throws InterruptedException {
        assertEquals(TurtleLocator.locateParallel(new StepPair[] {
                new StepPair(90.0, 2.0),
                new StepPair(90.0, 3.0),
                new StepPair(90.0, 2.0),
                new StepPair(0.0, 3.0)
        }, 2), new StatePair(3.9269908169872414, 4.242640687119286));
    }

}
