package de.jpvee.aoc2021;

import static de.jpvee.aoc2021.Parser.*;
import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.List;
import java.util.stream.IntStream;

public class Day05 extends Day<Pair<Pair<Long, Long>, Pair<Long, Long>>> {

    private int xMax = 0;
    private int yMax = 0;

    public Day05() {
        super(pairParser(
                pairParser(DECIMAL, DECIMAL, ","),
                pairParser(DECIMAL, DECIMAL, ","),
                "\\s+->\\s+"
        ));
        List<Pair<Pair<Long, Long>, Pair<Long, Long>>> data = getData();
        for (Pair<Pair<Long, Long>, Pair<Long, Long>> entry : data) {
            if (entry.getFirst().getFirst() > xMax) {
                xMax = Math.toIntExact(entry.getFirst().getFirst());
            }
            if (entry.getSecond().getFirst() > xMax) {
                xMax = Math.toIntExact(entry.getSecond().getFirst());
            }
            if (entry.getFirst().getSecond() > yMax) {
                yMax = Math.toIntExact(entry.getFirst().getSecond());
            }
            if (entry.getSecond().getSecond() > yMax) {
                yMax = Math.toIntExact(entry.getSecond().getSecond());
            }
        }
        xMax++;
        yMax++;
    }

    @Override
    public long solveOne() {
        return countHotSpots(countLines(false));
    }

    @Override
    public long solveTwo() {
        return countHotSpots(countLines(true));
    }

    private int[][] countLines(boolean countDiagonals) {
        int[][] count = new int[xMax][yMax];
        List<Pair<Pair<Long, Long>, Pair<Long, Long>>> data = getData();
        for (Pair<Pair<Long, Long>, Pair<Long, Long>> entry : data) {
            int x1 = Math.toIntExact(entry.getFirst().getFirst());
            int y1 = Math.toIntExact(entry.getFirst().getSecond());
            int x2 = Math.toIntExact(entry.getSecond().getFirst());
            int y2 = Math.toIntExact(entry.getSecond().getSecond());
            if (x1 == x2) {
                IntStream.rangeClosed(min(y1, y2), max(y1, y2)).forEach(y -> count[x1][y]++);
            } else if (y1 == y2) {
                IntStream.rangeClosed(min(x1, x2), max(x1, x2)).forEach(x -> count[x][y1]++);
            } else if (countDiagonals) {
                int d = abs(x1 - x2);
                for (int c = 0; c <= d; c++) {
                    int x = (c * x1 + (d - c) * x2) / d;
                    int y = (c * y1 + (d - c) * y2) / d;
                    count[x][y]++;
                }
            }
        }
        return count;
    }

    private long countHotSpots(int[][] count) {
        long result = 0;
        for (int x = 0; x < xMax; x++) {
            for (int y = 0; y < yMax; y++) {
                if (count[x][y] > 1) {
                    result++;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        printSolution();
    }

}
