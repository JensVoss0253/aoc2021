package de.jpvee.aoc2021;

import java.util.function.IntFunction;

public class Day07 extends Day<int[]> {

    public Day07() {
        super(Parser.INT_ARRAY);
    }

    @Override
    public long solveOne() {
        return solve(Math::abs);
    }

    @Override
    public long solveTwo() {
        return solve(dist -> Math.abs(dist) * (Math.abs(dist) + 1) / 2);
    }

    public long solve(IntFunction<Integer> cost) {
        int[] data = getData().get(0);
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int pos : data) {
            if (pos > max) {
                max = pos;
            }
            if (pos < min) {
                min = pos;
            }
        }
        long result = Integer.MAX_VALUE;
        for (int p = min; p <= max; p++) {
            int s = 0;
            for (int pos : data) {
                s += cost.apply(pos - p);
            }
            if (s < result) {
                result = s;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        printSolution();
    }

}
