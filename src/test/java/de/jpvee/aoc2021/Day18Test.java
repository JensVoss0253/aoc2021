package de.jpvee.aoc2021;

import org.junit.jupiter.api.Test;

import static de.jpvee.aoc2021.Day18.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class Day18Test extends DayTest {

    Day18Test() {
        super(4140L, 3993L);
    }

    @Test
    void testExplode() {
        assertEquals("[[[[0,9],2],3],4]", explode("[[[[[9,8],1],2],3],4]") );
        assertEquals("[7,[6,[5,[7,0]]]]", explode("[7,[6,[5,[4,[3,2]]]]]") );
        assertEquals("[[6,[5,[7,0]]],3]", explode("[[6,[5,[4,[3,2]]]],1]") );
        assertEquals("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]", explode("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]") );
        assertEquals("[[3,[2,[8,0]]],[9,[5,[7,0]]]]", explode("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]") );
        assertNull(explode("[[[[9,8],1],2],3]"));
    }

    @Test
    void testSplit() {
        assertEquals("[[[[0,7],4],[[7,8],[0,13]]],[1,1]]", split("[[[[0,7],4],[15,[0,13]]],[1,1]]"));
        assertEquals("[[[[0,7],4],[[7,8],[0,[6,7]]]],[1,1]]", split("[[[[0,7],4],[[7,8],[0,13]]],[1,1]]"));
        assertNull(split("[[[[0,7],4],[5,[0,3]]],[1,1]]"));
    }

    @Test
    void testAdd() {
        assertEquals("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]", add("[[[[4,3],4],4],[7,[[8,4],9]]]", "[1,1]"));
    }

    @Test
    void testMagnitude() {
        assertEquals(29, magnitude("[9,1]"));
        assertEquals(21, magnitude("[1,9]"));
        assertEquals(129, magnitude("[[9,1],[1,9]]"));
        assertEquals(143, magnitude("[[1,2],[[3,4],5]]"));
        assertEquals(1384, magnitude("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]"));
        assertEquals(445, magnitude("[[[[1,1],[2,2]],[3,3]],[4,4]]"));
        assertEquals(791, magnitude("[[[[3,0],[5,3]],[4,4]],[5,5]]"));
        assertEquals(1137, magnitude("[[[[5,0],[7,4]],[5,5]],[6,6]]"));
        assertEquals(3488, magnitude("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]"));
    }

}
