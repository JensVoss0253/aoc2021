package de.jpvee.aoc2021;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class Day23 extends Day<String> {

    private static final int[] ENERGY = {1, 10, 100, 1000};

    private static class State {

        private final int count;
        private final int[] positions;
        private final int[] pods;

        public State(String data) {
            int len = data.length();
            this.count = len / 4;
            this.positions = new int[len];
            this.pods = new int[11 + len];
            Arrays.fill(pods, -1);
            int[] d = new int[4];
            for (int i = 0; i < len; i++) {
                int x = data.charAt(i) - 'A';
                int pod = 11 + i;
                int pos = count * x + d[x]++;
                positions[pos] = pod;
                pods[pod] = pos;
            }
        }

        public State(State that) {
            this.count =that.count;
            this.positions = that.positions.clone();
            this.pods = that.pods.clone();
        }

        boolean isOccupied(int pos) {
            return pods[pos] >= 0;
        }

        boolean isRoomOnlyOccupiedByRightType(int type, int threshold) {
            for (int p = 11 + type; p < pods.length; p += 4) {
                if (p > threshold && pods[p] >= 0 && (pods[p] / count != type)) {
                    return false;
                }
            }
            return true;
        }

        boolean isHallwayClear(int start, int end) {
            if (start < end) {
                return IntStream.rangeClosed(start + 1, end).noneMatch(this::isOccupied);
            } else if (end < start){
                return IntStream.rangeClosed(end, start - 1).noneMatch(this::isOccupied);
            } else {
                return true;
            }
        }

        boolean isOrganized() {
            for (int p = 0; p < positions.length; p++) {
                if (positions[p] < 11 || (positions[p] + 1) % 4 != p / count) {
                    return false;
                }
            }
            return true;
        }

        List<Move> getAllowedMoves() {
            ArrayList<Move> result = new ArrayList<>();
            for (int p = 0; p < positions.length; p++) {
                int start = positions[p];
                int type = p / count;
                if (start < 11) {
                    if (isRoomOnlyOccupiedByRightType(type, 10)) {
                        int entrance = 2 + 2 * type;
                        if (isHallwayClear(start, entrance)) {
                            for (int d = count; d > 0; d--) {
                                int end = 7 + type + 4 * d;
                                if (!isOccupied(end)) {
                                    result.add(new Move(count, p, start, end));
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    int t = (start + 1) % 4;
                    int d = (start - 7) / 4;
                    int entrance = 2 + 2 * t;
                    if (t == type && isRoomOnlyOccupiedByRightType(t, start)) {
                        continue;
                    }
                    if (IntStream.range(1, d).noneMatch(e -> isOccupied(start - 4 * e)) && !isOccupied(entrance)) {
                        // go left
                        for (int end = entrance - 1; end >= 0; end -= 2) {
                            if (!isOccupied(end)) {
                                result.add(new Move(count, p, start, end));
                                if (end == 1 && !isOccupied(0)) {
                                    result.add(new Move(count, p, start, 0));
                                }
                            } else {
                                break;
                            }
                        }
                        // go right
                        for (int end = entrance + 1; end < 11; end += 2) {
                            if (!isOccupied(end)) {
                                result.add(new Move(count, p, start, end));
                                if (end == 9 && !isOccupied(10)) {
                                    result.add(new Move(count, p, start, 10));
                                }
                            } else {
                                break;
                            }
                        }
                        // go to side room
                        if (isRoomOnlyOccupiedByRightType(type, 10)) {
                            int entrance2 = 2 + 2 * type;
                            if (isHallwayClear(entrance, entrance2)) {
                                for (int d2 = count; d2 > 0; d2--) {
                                    int end = 7 + type + 4 * d2;
                                    if (!isOccupied(end)) {
                                        result.add(new Move(count, p, start, end));
                                        break;
                                    }
                                }
                            }
                        }
                    }

                }
            }
            return result;
        }

        public State apply(Move move) {
            State result = new State(this);
            result.positions[move.pod] = move.end;
            result.pods[move.start] = -1;
            result.pods[move.end] = move.pod;
            return result;
        }

    }

    private record Move(int count, int pod, int start, int end) {

        private static int getDistance(int start, int end) {
            if (start > end) {
                return getDistance(end, start);
            }
            int entrance = 2 + 2 * ((end + 1) % 4);
            int depth = (end - 7) / 4;
            if (start < 11) {
                return Math.abs(start - entrance) + depth;
            } else {
                return getDistance(entrance, start) + depth;
            }
        }

        int getEnergy() {
            return ENERGY[pod / count] * getDistance(start, end);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Move move = (Move) o;

            return count == move.count && pod == move.pod && start == move.start && end == move.end;
        }

        @Override
        public int hashCode() {
            int result = count;
            result = 31 * result + pod;
            result = 31 * result + start;
            result = 31 * result + end;
            return result;
        }

        @Override
        public String toString() {
            return "Move{" +
                    "count=" + count +
                    ", pod=" + pod +
                    ", start=" + start +
                    ", end=" + end +
                    '}';
        }
    }

    private final Deque<Move> help;

    public Day23() {
        super(Parser.STRING);
        help = new LinkedList<>();//Arrays.asList(HELP));
    }

    private void walk(State state, long energy, AtomicLong result) {
        if (energy > result.get()) {
            return;
        }
        if (state.isOrganized()) {
            if (energy < result.get()) {
                System.out.println("energy = " + energy);
                result.set(energy);
            }
        } else {
            List<Move> allowedMoves = state.getAllowedMoves();
            if (!help.isEmpty()) {
                Move move = help.removeFirst();
                allowedMoves = allowedMoves.stream().filter(move::equals).toList();
                if (allowedMoves.isEmpty()) {
                    throw new IllegalStateException("Move not allowed: " + move);
                }
            }
            allowedMoves.parallelStream().forEach(move -> walk(state.apply(move), energy + move.getEnergy(), result));
        }
    }

    @Override
    public long solveOne() {
        AtomicLong result = new AtomicLong(Long.MAX_VALUE);
        String data = String.join("", getData()).replaceAll("(\\s|#|\\.)", "");
        State state = new State(data);
        walk(state, 0L, result);
        return result.get();
    }

    @Override
    public long solveTwo() {
        AtomicLong result = new AtomicLong(Long.MAX_VALUE);
        String data = String.join("", getData()).replaceAll("(\\s|#|\\.)", "");
        data = data.substring(0, 4) + "DCBADBAC" + data.substring(4);
        State state = new State(data);
        walk(state, 0L, result);
        return result.get();
    }

    public static void main(String[] args) {
        printSolution();
    }

}
