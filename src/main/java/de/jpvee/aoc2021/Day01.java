package de.jpvee.aoc2021;

import java.util.List;

public class Day01 extends Day<Long> {

    public Day01() {
        super(Parser.DECIMAL);
    }

    @Override
    public long solveOne() {
        return solve(1);
    }

    @Override
    public long solveTwo() {
        return solve(3);
    }

    private int solve(int offset) {
        List<Long> data = getData();
        int len = data.size();
        int result = 0;
        for (int i = offset; i < len; i++) {
            if (data.get(i) > data.get(i - offset)) {
                result++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        printSolution();
    }

}
