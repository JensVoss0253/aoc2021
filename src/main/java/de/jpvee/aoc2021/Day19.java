package de.jpvee.aoc2021;

import static java.lang.Math.abs;
import static java.lang.Math.max;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day19 extends Day<Day19.Scanner> {

    public static class Point {
        private final int x;
        private final int y;
        private final int z;

        Point(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        Point plus(Point that) {
            return new Point(this.x + that.x, this.y + that.y, this.z + that.z);
        }

        Point minus(Point that) {
            return new Point(this.x - that.x, this.y - that.y, this.z - that.z);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Point point = (Point) o;

            return x == point.x && y == point.y && z == point.z;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            result = 31 * result + z;
            return result;
        }

        @Override
        public String toString() {
            return "<" + x + "|" + y + "|" + z + '>';
        }
    }

    public static class Rotation {

        static final Rotation[] INSTANCES = {
                new Rotation(1, 0, 0, 0, 1, 0, 0, 0, 1),
                new Rotation(1, 0, 0, 0, -1, 0, 0, 0, -1),
                new Rotation(1, 0, 0, 0, 0, 1, 0, -1, 0),
                new Rotation(1, 0, 0, 0, 0, -1, 0, 1, 0),
                new Rotation(-1, 0, 0, 0, 1, 0, 0, 0, -1),
                new Rotation(-1, 0, 0, 0, -1, 0, 0, 0, 1),
                new Rotation(-1, 0, 0, 0, 0, 1, 0, 1, 0),
                new Rotation(-1, 0, 0, 0, 0, -1, 0, -1, 0),
                new Rotation(0, 1, 0, 1, 0, 0, 0, 0, -1),
                new Rotation(0, 1, 0, -1, 0, 0, 0, 0, 1),
                new Rotation(0, 1, 0, 0, 0, 1, 1, 0, 0),
                new Rotation(0, 1, 0, 0, 0, -1, -1, 0, 0),
                new Rotation(0, -1, 0, 1, 0, 0, 0, 0, 1),
                new Rotation(0, -1, 0, -1, 0, 0, 0, 0, -1),
                new Rotation(0, -1, 0, 0, 0, 1, -1, 0, 0),
                new Rotation(0, -1, 0, 0, 0, -1, 1, 0, 0),
                new Rotation(0, 0, 1, 1, 0, 0, 0, 1, 0),
                new Rotation(0, 0, 1, -1, 0, 0, 0, -1, 0),
                new Rotation(0, 0, 1, 0, 1, 0, -1, 0, 0),
                new Rotation(0, 0, 1, 0, -1, 0, 1, 0, 0),
                new Rotation(0, 0, -1, 1, 0, 0, 0, -1, 0),
                new Rotation(0, 0, -1, -1, 0, 0, 0, 1, 0),
                new Rotation(0, 0, -1, 0, 1, 0, 1, 0, 0),
                new Rotation(0, 0, -1, 0, -1, 0, -1, 0, 0)
        };

        private final int[] m;

        public Rotation(int... m) {
            this.m = m;
        }

        public Rotation times(Rotation that) {
            return new Rotation(
                    this.m[0] * that.m[0] + this.m[1] * that.m[3] + this.m[2] * that.m[6],
                    this.m[0] * that.m[1] + this.m[1] * that.m[4] + this.m[2] * that.m[7],
                    this.m[0] * that.m[2] + this.m[1] * that.m[5] + this.m[2] * that.m[8],
                    this.m[3] * that.m[0] + this.m[4] * that.m[3] + this.m[5] * that.m[6],
                    this.m[3] * that.m[1] + this.m[4] * that.m[4] + this.m[5] * that.m[7],
                    this.m[3] * that.m[2] + this.m[4] * that.m[5] + this.m[5] * that.m[8],
                    this.m[6] * that.m[0] + this.m[7] * that.m[3] + this.m[8] * that.m[6],
                    this.m[6] * that.m[1] + this.m[7] * that.m[4] + this.m[8] * that.m[7],
                    this.m[6] * that.m[2] + this.m[7] * that.m[5] + this.m[8] * that.m[8]
            );
        }

        public Point apply(Point point) {
            return new Point(
                    m[0] * point.x + m[3] * point.y + m[6] * point.z,
                    m[1] * point.x + m[4] * point.y + m[7] * point.z,
                    m[2] * point.x + m[5] * point.y + m[8] * point.z
            );
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Rotation rotation = (Rotation) o;

            return Arrays.equals(m, rotation.m);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(m);
        }

        @Override
        public String toString() {
            return Arrays.toString(m);
        }
    }

    public static class Scanner {

        private final String name;
        private final Set<Point> scans = new HashSet<>();

        Scanner(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static class ScannerParser implements Parser<Scanner> {

        private Scanner current = null;

        @Override
        public Scanner parse(String input) {
            if (input == null || input.isBlank()) {
                current = null;
                return null;
            } else if (input.startsWith("---")) {
                current = new Scanner(input.substring(4, input.length() - 4));
                return current;
            } else {
                String[] pos = input.split(",");
                current.scans.add(
                        new Point(Integer.parseInt(pos[0]), Integer.parseInt(pos[1]), Integer.parseInt(pos[2])));
                return null;
            }
        }
    }

    public static class Adjustment {
        private final Rotation rotation;
        private final Point offset;

        public Adjustment(Rotation rotation, Point offset) {
            this.rotation = rotation;
            this.offset = offset;
        }

        Adjustment times(Adjustment second) {
            return new Adjustment(
                    second.rotation.times(this.rotation),
                    this.offset.plus(this.rotation.apply(second.offset))
            );
        }

    }

    private static Adjustment adjust(Scanner scanner, Scanner reference) {
        for (Rotation rotation : Rotation.INSTANCES) {
            for (Point point : scanner.scans) {
                for (Point referencePoint : reference.scans) {
                    Point offset = referencePoint.minus(rotation.apply(point));
                    long overlaps = scanner.scans.stream()
                                                 .map(rotation::apply)
                                                 .map(offset::plus)
                                                 .filter(reference.scans::contains)
                                                 .count();
                    if (overlaps >= 12) {
                        return new Adjustment(rotation, offset);
                    }
                }
            }
        }
        return null;
    }

    public Day19() {
        super(new ScannerParser());
    }

    private Map<Scanner, Adjustment> getScannerAdjustments() {
        List<Scanner> scanners = this.getData();
        Set<Scanner> unadjusted = new HashSet<>(scanners);
        Map<Scanner, Adjustment> adjusted = new HashMap<>();
        adjusted.put(scanners.get(0), new Adjustment(Rotation.INSTANCES[0], new Point(0, 0, 0)));
        unadjusted.remove(scanners.get(0));
        adjust:
        while (!unadjusted.isEmpty()) {
            for (Map.Entry<Scanner, Adjustment> reference : adjusted.entrySet()) {
                for (Scanner scanner : unadjusted) {
                    Adjustment adjustment = adjust(scanner, reference.getKey());
                    if (adjustment != null) {
                        adjusted.put(scanner, reference.getValue().times(adjustment));
                        unadjusted.remove(scanner);
                        continue adjust;
                    }
                }
            }
        }
        return adjusted;
    }

    @Override
    public long solveOne() {
        Map<Scanner, Adjustment> adjusted = getScannerAdjustments();
        Set<Point> beacons1 = new HashSet<>();
        for (Map.Entry<Scanner, Adjustment> entry : adjusted.entrySet()) {
            for (Point scan : entry.getKey().scans) {
                Point beacon = entry.getValue().offset.plus(entry.getValue().rotation.apply(scan));
                beacons1.add(beacon);
            }
        }
        return beacons1.size();
    }

    @Override
    public long solveTwo() {
        int result = 0;
        Map<Scanner, Adjustment> adjusted = getScannerAdjustments();
        for (Adjustment a1 : adjusted.values()) {
            Point b1 = a1.offset;
            for (Adjustment a2 : adjusted.values()) {
                Point b2 = a2.offset;
                result = max(result, abs(b1.x - b2.x) + abs(b1.y - b2.y) + abs(b1.z - b2.z));
            }
        }
        return result;
    }

    public static void main(String[] args) {
        printSolution();
    }

}
