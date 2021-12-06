package de.jpvee.aoc2021;

import java.util.Arrays;

public class Day06 extends Day<int[]> {

    public Day06() {
        super(Parser.INT_ARRAY);
    }

    private long[] advance(long[] timers) {
        return new long[] {
                timers[1], timers[2], timers[3], timers[4], timers[5], timers[6], timers[7] + timers[0], timers[8], timers[0]
        };
    }

    @Override
    public long solveOne() {
        return solve(80);
    }

    @Override
    public long solveTwo() {
        return solve(256);
    }

    private long solve(int i) {
        long timers[] = new long[9];
        for (int t : getData().get(0)) {
            timers[t]++;
        }
        for (int day = 0; day < i; day++) {
            timers = advance(timers);
        }
        return Arrays.stream(timers).sum();
    }

    public static void main(String[] args) {
        printSolution();
    }

}
