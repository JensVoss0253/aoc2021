package de.jpvee.aoc2021;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day22Test extends DayTest {

    Day22Test() {
        super(39L, 39L);
    }

    @Test
    void solveOneA() {
        Day22 day = new Day22("Day22a.txt");
        assertEquals(590784, day.solveOne());
    }

    @Test
    void solveOneB() {
        Day22 day = new Day22("Day22b.txt");
        assertEquals(474140, day.solveOne());
    }

    @Test
    void solveTwoB() {
        Day22 day = new Day22("Day22b.txt");
        assertEquals(2758514936282235L, day.solveTwo());
    }

}
