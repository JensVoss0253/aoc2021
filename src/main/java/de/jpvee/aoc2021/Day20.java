package de.jpvee.aoc2021;

import java.util.Arrays;
import java.util.List;

public class Day20 extends Day<String> {

    private static final int[][] NEIGHBORS = {
            { -1, -1 }, { 0, -1 }, { 1, -1 }, { -1, 0 }, { 0, 0 }, { 1, 0 }, { -1, 1 }, { 0, 1 }, { 1, 1 }
    };

    private final String algorithm;
    private final String[] start;

    public Day20() {
        super(Parser.STRING);
        List<String> data = getData();
        this.algorithm = data.get(0);
        this.start = data.stream().skip(2).toArray(String[]::new);
    }

    private int read(int x, int y, String[] data, char outside) {
        char c = y < 0 || y >= data.length || x < 0 || x >= data[y].length() ? outside : data[y].charAt(x);
        return c == '#' ? 1 : 0;
    }

    private String[] enhance(String[] input, char outside) {
        String[] output = new String[input.length + 2];
        int width = input[0].length() + 2;
        for (int y = 0; y < output.length; y++) {
            StringBuilder buf = new StringBuilder();
            for (int x = 0; x < width; x++) {
                int code = 0;
                for (int[] neighbor : NEIGHBORS) {
                    code = (code << 1) + read(x + neighbor[0] - 1, y + neighbor[1] - 1, input, outside);
                }
                buf.append(algorithm.charAt(code));
            }
            output[y] = buf.toString();
        }
        return output;
    }

    @Override
    public long solveOne() {
        String[] image = start;
        image = enhance(image, '.');
        char outside = algorithm.charAt(0);
        image = enhance(image, outside);
        return Arrays.stream(image).mapToInt(str -> (int) str.chars().filter(i -> i == '#').count()).sum();
    }

    @Override
    public long solveTwo() {
        String[] image = start;
        char outside = '.';
        for (int i = 0, parity = 0; i < 50; i++, parity = 1 - parity) {
            image = enhance(image, outside);
            outside = algorithm.charAt(outside == '.' ? 0 : 511);
        }
        return Arrays.stream(image).mapToInt(str -> (int) str.chars().filter(i -> i == '#').count()).sum();
    }

    public static void main(String[] args) {
        printSolution();
    }

}
