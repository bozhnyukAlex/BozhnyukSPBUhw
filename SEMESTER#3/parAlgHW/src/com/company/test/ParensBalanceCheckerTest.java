package com.company.test;

import com.company.parensTask.ParensBalanceChecker;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class ParensBalanceCheckerTest {
    @Test
    public void rightString() {
        assertTrue(ParensBalanceChecker.check("(((()()()())))"));
    }

    @Test
    public void badStrings() {
        assertFalse(ParensBalanceChecker.check("))(("));
        assertFalse(ParensBalanceChecker.check("(((()()))))"));
    }

    @Test
    public void rightStringParallel() throws InterruptedException {
        assertTrue(ParensBalanceChecker.checkParallel("(((()()()())))", 4));
    }

    @Test
    public void badStringsParallel() throws InterruptedException {
        assertFalse(ParensBalanceChecker.checkParallel("))((", 2));
        assertFalse(ParensBalanceChecker.checkParallel("(((()()))))", 4));
    }
 }
