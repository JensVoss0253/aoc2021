package de.jpvee.aoc2021;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class Day11 extends Day<String> {

    private final int width;
    private final int height;
    private final int[][] data;
    private final boolean[][] flash;

    public Day11() {
        super(Parser.STRING);
        List<String> data = getData();
        this.width = data.get(0).length();
        this.height = data.size();
        this.data = new int[height][width];
        for (int i = 0; i < height; i++) {
            String str = data.get(i);
            for (int j = 0; j < width; j++) {
                this.data[i][j] = str.charAt(j) - '0';
            }
        }
        this.flash = new boolean[height][width];
    }

    private void increment(int row, int col) {
        if (row >= 0 && row < height && col >= 0 && col < width) {
            data[row][col]++;
            if (data[row][col] > 9 && !flash[row][col]) {
                flash[row][col] = true;
                increment(row - 1, row + 2, col - 1, col + 2);
            }
        }
    }

    private void increment(int r0, int r1, int c0, int c1) {
        IntStream.range(r0, r1).forEach(i -> IntStream.range(c0, c1).forEach(j -> increment(i, j)));
    }

    private long increment() {
        increment(0, height, 0, width);
        AtomicLong result = new AtomicLong();
        IntStream.range(0, height).forEach(i -> IntStream.range(0, width).forEach(j -> {
            if (flash[i][j]) {
                data[i][j] = 0;
                flash[i][j] = false;
                result.incrementAndGet();
            }
        }));
        return result.get();
    }

    @Override
    public long solveOne() {
        return IntStream.range(0, 100).mapToLong(i -> increment()).sum();
    }

    @Override
    public long solveTwo() {
        long count = 0L;
        while (true) {
            count++;
            if (increment() == (long) height * width) {
                return count;
            }
        }
    }

    public static void main(String[] args) {
        printSolution();
    }

}
