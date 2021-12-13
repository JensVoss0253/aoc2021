package de.jpvee.aoc2021;

import java.util.ArrayList;
import java.util.List;

public class Day13 extends Day<String> {

    private final boolean[][] data;
    private int height = 0;
    private int width = 0;

    private final List<String> folds = new ArrayList<>();

    public Day13() {
        super(Parser.STRING);
        for (String line : getData()) {
            if (line == null || line.trim().length() == 0) {
                continue;
            }
            if (line.startsWith("fold along ")) {
                folds.add(line.substring(11));
            } else {
                String[] split = line.split(",");
                int x = Integer.parseInt(split[0]);
                int y = Integer.parseInt(split[1]);
                if (x > width) {
                    width = x;
                }
                if (y > height) {
                    height = y;
                }
            }
        }
        data = new boolean[++width][++height];
        for (String line : getData()) {
            if (line != null && line.trim().length() != 0 && !line.startsWith("fold along ")) {
                String[] split = line.split(",");
                int x = Integer.parseInt(split[0]);
                int y = Integer.parseInt(split[1]);
                data[x][y] = true;
            }
        }
    }

    @Override
    public long solveOne() {
        String fold = folds.get(0);
        fold(fold);
        long dots = 0L;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (data[i][j]) {
                    dots++;
                }
            }
        }
        return dots;
    }

    @Override
    public long solveTwo() {
        folds.forEach(this::fold);
        System.out.println();
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                System.out.print(data[i][j] ? "##" : "  ");
            }
            System.out.println();
        }
        System.out.println();
        return 0;
    }

    private void fold(String fold) {
        int f = Integer.parseInt(fold.substring(2));
        if (fold.startsWith("x=")) {
            foldLeft(f);
        } else if (fold.startsWith("y=")) {
            foldUp(f);
        }
    }

    private void foldLeft(int f) {
        for (int y = 0; y < height; y++) {
            for (int x = f + 1; x < width; x++) {
                if (data[x][y]) {
                    data[2 * f - x][y] = true;
                }
            }
        }
        width = f;
    }

    private void foldUp(int f) {
        for (int x = 0; x < width; x++) {
            for (int y = f + 1; y < height; y++) {
                if (data[x][y]) {
                    data[x][2 * f - y] = true;
                }
            }
        }
        height = f;
    }

    public static void main(String[] args) {
        printSolution();
    }

}
