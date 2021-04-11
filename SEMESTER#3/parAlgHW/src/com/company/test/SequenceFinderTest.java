package com.company.test;
import com.company.sequenceTask.Pair;
import com.company.sequenceTask.SequenceFinder;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class SequenceFinderTest {
    @Test
    public void check() {
        assertEquals(SequenceFinder.find(new Pair[] {new Pair(0,3), new Pair(1, 2), new Pair(2, 3)}), 13);
    }

    @Test
    public void checkParallel() throws InterruptedException {
        assertEquals(SequenceFinder.findParallel(new Pair[] {new Pair(0,3), new Pair(1, 2), new Pair(2, 3)}, 2), 13);
    }
}
