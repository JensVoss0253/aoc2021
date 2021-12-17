package de.jpvee.aoc2021;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day17 extends Day<String> {

    private final int x1;
    private final int x2;
    private final int y1;
    private final int y2;

    public Day17() {
        super(Parser.STRING);
        Pattern pattern = Pattern.compile("target area: x=(.*)\\.\\.(.*), y=(.*)\\.\\.(.*)");
        Matcher matcher = pattern.matcher(getData().get(0));
        if (!matcher.matches()) {
            throw new IllegalStateException();
        }
        x1 = Integer.parseInt(matcher.group(1));
        x2 = Integer.parseInt(matcher.group(2));
        y1 = Integer.parseInt(matcher.group(3));
        y2 = Integer.parseInt(matcher.group(4));
    }

    @Override
    public long solveOne() {
        return (long) y1 * (y1 + 1) / 2;
    }

    @Override
    public long solveTwo() {
        long result = 0;
        for (int p0 = 0; p0 <= x2; p0++) {
            for (int q0 = y1; q0 < -y1; q0++) {
                for (int p = p0, q = q0, x = 0, y = 0; y >= y1; x += p, y += q, p = Math.max(p - 1, 0), q--) {
                    if (x1 <= x && x <= x2 && y1 <= y && y <= y2) {
                        result++;
                        break;
                    }
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        printSolution();
    }

}
