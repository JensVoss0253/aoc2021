package de.jpvee.aoc2021;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Day16Test extends DayTest {

    Day16Test() {
        super(6L, 2021L);
    }

    @Override
    @BeforeEach
    void setUp() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        super.setUp();
    }

    @Test
    void solveOneA() {
        assertEquals(9L, new Day16("38006F45291200").solveOne());
        assertEquals(14L, new Day16("EE00D40C823060").solveOne());
        assertEquals(16L, new Day16("8A004A801A8002F478").solveOne());
        assertEquals(12L, new Day16("620080001611562C8802118E34").solveOne());
        assertEquals(23L, new Day16("C0015000016115A2E0802F182340").solveOne());
        assertEquals(31L, new Day16("A0016C880162017C3686B18A3D4780").solveOne());
    }

    @Test
    void solveTwoA() {
        assertEquals(3L, new Day16("C200B40A82").solveTwo());
        assertEquals(54L, new Day16("04005AC33890").solveTwo());
        assertEquals(7L, new Day16("880086C3E88112").solveTwo());
        assertEquals(9L, new Day16("CE00C43D881120").solveTwo());
        assertEquals(1L, new Day16("D8005AC2A8F0").solveTwo());
        assertEquals(0L, new Day16("F600BC2D8F").solveTwo());
        assertEquals(0L, new Day16("9C005AC2F8F0").solveTwo());
        assertEquals(1L, new Day16("9C0141080250320F1802104A08").solveTwo());
    }

}
