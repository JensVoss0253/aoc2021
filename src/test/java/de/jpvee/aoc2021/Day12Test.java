package de.jpvee.aoc2021;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day12Test extends DayTest {

    private Day12 day12a;
    private Day12 day12b;

    @Override
    @BeforeEach
    void setUp() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        super.setUp();
        day12a = new Day12("Day12a.txt");
        day12b = new Day12("Day12b.txt");
    }

    Day12Test() {
        super(10L, 36L);
    }

    @Test
    void solveOneA() {
        assertEquals(19, day12a.solveOne());
    }

    @Test
    void solveOneB() {
        assertEquals(226, day12b.solveOne());
    }

    @Test
    void solveTwoA() {
        assertEquals(103, day12a.solveTwo());
    }

    @Test
    void solveTwoB() {
        assertEquals(3509, day12b.solveTwo());
    }

}
