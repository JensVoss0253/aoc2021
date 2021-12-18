package de.jpvee.aoc2021;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day18 extends Day<String> {

    private static final Pattern EXPLODE_LEFT = Pattern.compile("^(.*)\\[(\\d+)$");
    private static final Pattern EXPLODE_RIGHT = Pattern.compile("^(\\d+)](.*)$");
    private static final Pattern EXPLODE_LAST_NUMBER = Pattern.compile("^(.*[^\\d])(\\d+)([^\\d]+)$");
    private static final Pattern EXPLODE_FIRST_NUMBER = Pattern.compile("^([^\\d]+)(\\d+)([^\\d].*)$");

    public static String explode(String number) {
        int d = 0, p = -1;
        find:
        while (++p < number.length()) {
            switch (number.charAt(p)) {
                case '[' -> d++;
                case ']' -> d--;
                case ',' -> {
                    if (d > 4 && Character.isDigit(number.charAt(p + 1))) {
                        break find;
                    }
                }
            }
        }
        if (p == number.length()) {
            return null;
        }
        Matcher left = EXPLODE_LEFT.matcher(number.substring(0, p));
        Matcher right = EXPLODE_RIGHT.matcher(number.substring(p + 1));
        if (!left.matches() || !right.matches()) {
            throw new IllegalArgumentException(number);
        }
        String start = left.group(1);
        String end = right.group(2);
        StringBuilder result = new StringBuilder();
        Matcher lastNumber = EXPLODE_LAST_NUMBER.matcher(start);
        if (lastNumber.matches()) {
            result.append(lastNumber.group(1));
            result.append(Long.parseLong(lastNumber.group(2)) + Long.parseLong(left.group(2)));
            result.append(lastNumber.group(3));
        } else {
            result.append(start);
        }
        result.append("0");
        Matcher firstNumber = EXPLODE_FIRST_NUMBER.matcher(end);
        if (firstNumber.matches()) {
            result.append(firstNumber.group(1));
            result.append(Long.parseLong(right.group(1)) + Long.parseLong(firstNumber.group(2)));
            result.append(firstNumber.group(3));
        } else {
            result.append(end);
        }
        return result.toString();
    }

    public static String split(String number) {
        int p = -1;
        while (++p < number.length() - 1) {
            if (Character.isDigit(number.charAt(p)) && Character.isDigit(number.charAt(p + 1))) {
                int q;
                for (q = p + 1; Character.isDigit(number.charAt(q)); q++);
                long val = Long.parseLong(number.substring(p, q));
                return "%s[%d,%d]%s".formatted(number.substring(0, p), val / 2, val - (val / 2), number.substring(q));
            }
        }
        return null;
    }

    public static String add(String first, String second) {
        return reduce("[" + first + "," + second + "]");
    }

    public static String reduce(String number) {
        String result = explode(number);
        if (result != null) {
            return reduce(result);
        } else {
            result = split(number);
            return result != null ? reduce(result) : number;
        }
    }

    public static long magnitude(String number) {
        Pattern pattern = Pattern.compile("^(.*)\\[(\\d+),(\\d+)](.*)$");
        for (Matcher matcher = pattern.matcher(number); matcher.matches(); matcher = pattern.matcher(number)) {
            number = matcher.group(1);
            number += (3 * Long.parseLong(matcher.group(2)) + 2 * Long.parseLong(matcher.group(3))) + matcher.group(4);
        }
        return Long.parseLong(number);
    }

    public Day18() {
        super(Parser.STRING);
    }

    @Override
    public long solveOne() {
        List<String> data = getData();
        String sum = data.get(0);
        for (int i = 1; i < data.size(); i++) {
            sum = add(sum, data.get(i));
        }
        return magnitude(sum);
    }

    @Override
    public long solveTwo() {
        long record = 0;
        List<String> data = getData();
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.size(); j++) {
                if (i != j) {
                    record = Math.max(record, magnitude(add(data.get(i), data.get(j))));
                }
            }
        }
        return record;
    }

    public static void main(String[] args) {
        printSolution();
    }

}
