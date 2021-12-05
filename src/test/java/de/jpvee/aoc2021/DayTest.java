package de.jpvee.aoc2021;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

abstract class DayTest {

    private final long one;
    private final long two;

    private Day<?> day;

    DayTest(long one, long two) {
        this.one = one;
        this.two = two;
    }

    @BeforeEach
    void setUp() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        Class<?> dayClass = Class.forName(this.getClass().getName().replace("Test", ""));
        day = (Day<?>) dayClass.getConstructor().newInstance();
    }

    @Test
    void solveOne() {
        assertEquals(one, day.solveOne());
    }

    @Test
    void solveTwo() {
        assertEquals(two, day.solveTwo());
    }

}
