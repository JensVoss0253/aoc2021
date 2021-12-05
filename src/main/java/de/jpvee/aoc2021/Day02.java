package de.jpvee.aoc2021;

import java.util.List;

public class Day02 extends Day<Pair<Day02.Direction, Long>> {

    enum Direction {
        forward, down, up;
    }

    public Day02() {
        super(Parser.pairParser(Parser.enumParser(Direction.class), Parser.DECIMAL));
    }

    @Override
    public long solveOne() {
        List<Pair<Direction, Long>> data = getData();
        long horizontal = 0;
        long depth = 0;
        for (Pair<Direction, Long> pair : data) {
            switch (pair.getFirst()) {
            case forward -> horizontal += pair.getSecond();
            case down -> depth += pair.getSecond();
            case up -> depth -= pair.getSecond();
            }
        }
        return horizontal * depth;
    }

    @Override
    public long solveTwo() {
        List<Pair<Direction, Long>> data = getData();
        long horizontal = 0;
        long depth = 0;
        long aim = 0;
        for (Pair<Direction, Long> pair : data) {
            switch (pair.getFirst()) {
            case forward -> {
                horizontal += pair.getSecond();
                depth += aim * pair.getSecond();
            }
            case down -> aim += pair.getSecond();
            case up -> aim -= pair.getSecond();
            }
        }
        return horizontal * depth;
    }

    public static void main(String[] args) {
        printSolution();
    }

}
