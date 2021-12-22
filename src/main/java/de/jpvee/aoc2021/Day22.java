package de.jpvee.aoc2021;

import java.util.*;

public class Day22 extends Day<String> {

    @SuppressWarnings("unused")
    public Day22() {
        super(Parser.STRING);
    }

    public Day22(String inputPath) {
        super(inputPath, Parser.STRING);
    }

    @Override
    public long solveOne() {
        boolean[][][] cubes = new boolean[101][101][101];
        for (String step : getData()) {
            boolean on = step.startsWith("on");
            int[][] coord = Arrays.stream(step.substring(on ? 3 : 4).split(","))
                    .map(r -> Arrays.stream(r.substring(2).split("\\.\\.")).mapToInt(Integer::parseInt).toArray())
                    .toArray(int[][]::new);
            if (coord[0][0] < -50 || coord[0][1]  > 50
                    || coord[1][0] < -50 || coord[1][1]  > 50
                    || coord[2][0] < -50 || coord[2][1]  > 50) {
                continue;
            }
            for (int x = coord[0][0]; x <= coord[0][1]; x++) {
                for (int y = coord[1][0]; y <= coord[1][1]; y++) {
                    for (int z = coord[2][0]; z <= coord[2][1]; z++) {
                        cubes[x+50][y+50][z+50] = on;
                    }
                }
            }

        }
        long result = 0L;
        for (int x = 0; x < 101; x++) {
            for (int y = 0; y < 101; y++) {
                for (int z = 0; z < 101; z++) {
                    if (cubes[x][y][z]) {
                        result++;
                    }
                }
            }
        }
        return result;
    }

    private static class Range {
        final int x1, x2, y1, y2, z1, z2;

        public Range(int x1, int x2, int y1, int y2, int z1, int z2) {
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;
            this.z1 = z1;
            this.z2 = z2;
        }

        Range(int[][] coord) {
            this.x1 = coord[0][0];
            this.x2 = coord[0][1];
            this.y1 = coord[1][0];
            this.y2 = coord[1][1];
            this.z1 = coord[2][0];
            this.z2 = coord[2][1];
        }

        long getSize() {
            return (1L + x2 - x1) * (1L + y2 - y1) * (1L + z2 - z1);
        }

        List<Range> minus(Range that) {
            if (that.x2 < this.x1 || this.x2 < that.x1 || that.y2 < this.y1 || this.y2 < that.y1 || that.z2 < this.z1 || this.z2 < that.z1) {
                return List.of(this);
            }
            List<Range> result = new ArrayList<>();
            int p1, p2, q1, q2;
            if (this.x1 < that.x1) {
                result.add(new Range(this.x1, that.x1 - 1, this.y1, this.y2, this.z1, this.z2));
                p1 = that.x1;
            } else {
                p1 = this.x1;
            }
            if (that.x2 < this.x2) {
                result.add(new Range(that.x2 + 1, this.x2, this.y1, this.y2, this.z1, this.z2));
                p2 = that.x2;
            } else {
                p2 = this.x2;
            }
            if (this.y1 < that.y1) {
                result.add(new Range(p1, p2, this.y1, that.y1 - 1, this.z1, this.z2));
                q1 = that.y1;
            } else {
                q1 = this.y1;
            }
            if (that.y2 < this.y2) {
                result.add(new Range(p1, p2, that.y2 + 1, this.y2, this.z1, this.z2));
                q2 = that.y2;
            } else {
                q2 = this.y2;
            }
            if (this.z1 < that.z1) {
                result.add(new Range(p1, p2, q1, q2, this.z1, that.z1 - 1));
            }
            if (that.z2 < this.z2) {
                result.add(new Range(p1, p2, q1, q2, that.z2 + 1, this.z2));
            }
            return result;
        }
    }

    @Override
    public long solveTwo() {
        List<Range> existing = new ArrayList<>();
        for (String step : getData()) {
            boolean on = step.startsWith("on");
            int[][] coord = Arrays.stream(step.substring(on ? 3 : 4).split(","))
                    .map(r -> Arrays.stream(r.substring(2).split("\\.\\.")).mapToInt(Integer::parseInt).toArray())
                    .toArray(int[][]::new);
            Range range = new Range(coord);
            if (on) {
                List<Range> additional = new ArrayList<>();
                additional.add(range);
                for (Range e : existing) {
                    List<Range> additional2 = new ArrayList<>();
                    for (Range a : additional) {
                        additional2.addAll(a.minus(e));
                    }
                    additional.clear();
                    additional.addAll(additional2);
                }
                existing.addAll(additional);
            } else {
                List<Range> existing2 = existing.stream().flatMap(e -> e.minus(range).stream()).toList();
                existing.clear();
                existing.addAll(existing2);
            }
        }
        return existing.stream().mapToLong(Range::getSize).sum();
    }

    public static void main(String[] args) {
        printSolution();
    }

}
