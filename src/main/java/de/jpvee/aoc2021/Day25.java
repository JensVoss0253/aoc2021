package de.jpvee.aoc2021;

public class Day25 extends Day<String> {

    private final int width;
    private final int height;
    private final char[][] locations;

    public Day25() {
        super(Parser.STRING);
        locations = getData().stream().map(String::toCharArray).toArray(char[][]::new);
        width = locations[0].length;
        height = locations.length;
    }

    @Override
    public long solveOne() {
        long result = 1L;
        while (true) {
            if (move()) {
                result++;
            } else {
                return result;
            }
        }
    }

    private boolean move() {
        return moveEast() | moveSouth();
    }

    private boolean moveEast() {
        boolean result = false;
        boolean[][] check = new boolean[height][width];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                check[y][x] = locations[y][x] == '>' && locations[y][(x + 1) % width] == '.';
            }
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (check[y][x]) {
                    result = true;
                    locations[y][x] = '.';
                    locations[y][(x + 1) % width] = '>';
                }
            }
        }
        return result;
    }

    private boolean moveSouth() {
        boolean result = false;
        boolean[][] check = new boolean[height][width];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                check[y][x] = locations[y][x] == 'v' && locations[(y + 1) % height][x] == '.';
            }
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (check[y][x]) {
                    result = true;
                    locations[y][x] = '.';
                    locations[(y + 1) % height][x] = 'v';
                }
            }
        }
        return result;
    }

    @Override
    public long solveTwo() {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        printSolution();
    }

}
