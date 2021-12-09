package de.jpvee.aoc2021;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day09 extends Day<String> {

    interface Part {

        void collectLowPoint(int x, int y);
        long getResult();

    }

    class One implements Part {

        private long sum;

        @Override
        public void collectLowPoint(int x, int y) {
            sum += data[x][y] + 1;
        }

        @Override
        public long getResult() {
            return sum;
        }
    }

    class Two implements Part {

        List<Long> basins = new ArrayList<>();

        @Override
        public void collectLowPoint(int x, int y) {
            boolean[][] basin = new boolean[height][width];
            basin[x][y] = true;
            boolean increment;
            do {
                increment = false;
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        if (basin[i][j] || data[i][j] == 9) {
                            continue;
                        }
                        if ((i > 0 && basin[i-1][j])
                            || (i < height - 1 && basin[i+1][j])
                            || (j > 0 && basin[i][j-1])
                            || (j < width - 1 && basin[i][j+1])) {

                            basin[i][j] = true;
                            increment = true;

                        }
                    }
                }

            } while (increment);
            long size = 0;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (basin[i][j]) {
                        size++;
                    }
                }
            }
            basins.add(size);
        }

        @Override
        public long getResult() {
            basins.sort(Comparator.reverseOrder());
            return basins.get(0) * basins.get(1) * basins.get(2);
        }
    }

    private final int width;
    private final int height;
    private final int[][] data;

    public Day09() {
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
    }

    @Override
    public long solveOne() {
        return solve(new One()).getResult();
    }

    @Override
    public long solveTwo() {
        return solve(new Two()).getResult();
    }

    public Part solve(Part part) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int v = data[i][j];
                if ((i == 0 || v < data[i - 1][j])
                    && (i == height - 1 || v < data[i + 1][j])
                    && (j == 0 || v < data[i][j - 1])
                    && (j == width - 1 || v < data[i][j + 1])) {

                    part.collectLowPoint(i, j);

                }
            }
        }
        return part;
    }


    public static void main(String[] args) {
        printSolution();
    }

}
