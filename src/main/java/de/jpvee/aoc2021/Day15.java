package de.jpvee.aoc2021;

import java.util.Arrays;
import java.util.List;

public class Day15 extends Day<String> {

    private static final int[][] NEIGHBORS = { {-1, 0}, {0, -1}, {0, 1}, {1, 0} };
    private static final long MAX = Long.MAX_VALUE >> 1;

    private int width;
    private int height;
    private int[][] data;
    private long[][] risks;

    public Day15() {
        super(Parser.STRING);
    }

    private void init(int factor) {
        List<String> data = getData();
        this.width = data.get(0).length();
        this.height = data.size();
        this.data = new int[factor * width][factor * height];
        for (int j = 0; j < height; j++) {
            String str = data.get(j);
            for (int i = 0; i < width; i++) {
                this.data[i][j] = str.charAt(i) - '0';
                for (int p = 0; p < factor; p++) {
                    for (int q = 0; q < factor; q++) {
                        if (p + q > 0) {
                            this.data[i + p * width][j + q * height] = (this.data[i][j] + p + q - 1) % 9 + 1;
                        }
                    }
                }
            }
        }
        this.width *= factor;
        this.height *= factor;
        this.risks = new long[height][width];
        Arrays.stream(this.risks).forEach(arr -> Arrays.fill(arr, MAX));
        this.risks[height-1][width-1] = 0;
    }

    private long getData(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return MAX;
        }
        return this.data[y][x];
    }

    private long getRisk(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return MAX;
        }
        return this.risks[y][x];
    }

    private long solve() {
        boolean changes;
        do {
            changes = false;
            for (int i = 1; i < height + width; i++) {
                for (int h = 0; h < height; h++) {
                    int y = height - 1 - h;
                    int x = width - (i - h) - 1;
                    if (x >= 0 && x < width) {
                        long risk = risks[y][x];
                        for (int[] neighbor : NEIGHBORS) {
                            risk = Math.min(risk, getData(x + neighbor[0], y + neighbor[1]) + getRisk(x + neighbor[0],y + neighbor[1]));
                        }
                        if (risk < risks[y][x]) {
                            changes = true;
                            risks[y][x] = risk;
                        }
                    }
                }
            }
        } while (changes);
        return risks[0][0];
    }

    @Override
    public long solveOne() {
        init(1);
        return solve();
    }


    @Override
    public long solveTwo() {
        init(5);
        return solve();
    }

    public static void main(String[] args) {
        printSolution();
    }

}
