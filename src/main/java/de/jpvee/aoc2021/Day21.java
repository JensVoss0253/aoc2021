package de.jpvee.aoc2021;

public class Day21 extends Day<String> {

    private static final long[] MULTIPLICITIES = new long[] {1L, 3L, 6L, 7L, 6L, 3L, 1L};

    private static class State {

        private final long multiplicity;
        private final State[] children;
        private final int winner;

        State(long multiplicity, int[] pos, int[] score, int player) {
            this.multiplicity = multiplicity;
            if (score[0] > 20) {
                winner = 0;
                children = null;
            } else if (score[1] > 20) {
                winner = 1;
                children = null;
            } else {
                winner = -1;
                children = new State[7];
                for (int m = 0; m < 7; m++) {
                    int[] pos2 = pos.clone();
                    pos2[player] = 1 + (pos2[player] + 2 + m) % 10;
                    int[] score2 = score.clone();
                    score2[player] += pos2[player];
                    children[m] = new State(MULTIPLICITIES[m], pos2, score2, 1 - player);
                }
            }
        }

        public long[] countResults() {
            if (winner == 0) {
                return new long[] {multiplicity, 0};
            } else if (winner == 1) {
                return new long[] {0, multiplicity};
            } else {
                long[] results = new long[2];
                for (int m = 0; m < 7; m++) {
                    long[] partial = children[m].countResults();
                    results[0] += multiplicity * partial[0];
                    results[1] += multiplicity * partial[1];
                }
                return results;
            }
        }
    }

    private final int[] pos;
    private final long[] score;
    private int die = 0;
    private int rolls = 0;
    private int player = 0;

    public Day21() {
        super(Parser.STRING);
        this.pos = new int[2];
        this.score = new long[2];
        pos[0] = Integer.parseInt(getData().get(0).substring(28));
        pos[1] = Integer.parseInt(getData().get(1).substring(28));
    }

    @Override
    public long solveOne() {
        while (true) {
            pos[player] = 1 + (pos[player] + 3 * die + 5) % 10;
            score[player] += pos[player];
            die = (die + 3) % 100;
            rolls += 3;
            if (score[player] >= 1000) {
                return score[1 - player] * rolls;
            } else {
                player = 1 - player;
            }
        }
    }

    @Override
    public long solveTwo() {
        State root = new State(1, this.pos, new int[2], 0);
        long[] results = root.countResults();
        return Math.max(results[0], results[1]);
    }

    public static void main(String[] args) {
        printSolution();
    }

}
