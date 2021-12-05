package de.jpvee.aoc2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class Day04 extends Day<String> {

    private final int[] numbers;
    private final int[][][] boards;

    public Day04() {
        super(Parser.STRING);
        List<String> data = getData();
        numbers = Arrays.stream(data.get(0).split(",")).mapToInt(Integer::parseInt).toArray();
        int b = (data.size() - 1) / 6;
        boards = new int[b][5][];
        for (int i = 0; i < b; i++) {
            for (int j = 0; j < 5; j++) {
                boards[i][j] = Arrays.stream(data.get(6*i + j + 2).trim().split("\\s+", 5)).mapToInt(Integer::parseInt).toArray();
            }
        }
    }

    @Override
    public long solveOne() {
        return play(integers -> !integers.isEmpty());
    }

    @Override
    public long solveTwo() {
        return play(integers -> integers.size() == boards.length);
    }

    private long play(Predicate<List<Integer>> finished) {
        int b = boards.length;
        int[][][] drawn = new int[b][5][5];
        List<Integer> winners = new ArrayList<>();
        for (int n = 0; n < numbers.length; n++) {
            // draw
            for (int i = 0; i < b; i++) {
                for (int j = 0; j < 5; j++) {
                    for (int k = 0; k < 5; k++) {
                        if (boards[i][j][k] == numbers[n]) {
                            drawn[i][j][k] = 1;
                        }
                    }
                }
            }
            // check
            for (int i = 0; i < b; i++) {
                int i1 = i;
                if (winners.stream().anyMatch(w -> w.equals(i1))) {
                    continue;
                }
                for (int j = 0; j < 5; j++) {
                    int j1 = j;
                    if (Arrays.stream(drawn[i1][j1], 0, 5).sum() == 5) {
                        winners.add(i);
                        break;
                    }
                    if (IntStream.range(0, 5).map(k -> drawn[i1][k][j1]).sum() == 5) {
                        winners.add(i);
                        break;
                    }
                }
            }
            if (finished.test(winners)) {
                int winner = winners.get(winners.size() - 1);
                long sum = 0;
                for (int j = 0; j < 5; j++) {
                    for (int k = 0; k < 5; k++) {
                        if (drawn[winner][j][k] == 0) {
                            sum += boards[winner][j][k];
                        }
                    }
                }
                return sum * numbers[n];
            }
        }
        throw new IllegalStateException("No Bingo row found.");
    }

    public static void main(String[] args) {
        printSolution();
    }

}
