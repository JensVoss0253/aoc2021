package de.jpvee.aoc2021;

import static de.jpvee.aoc2021.Parser.arrayParser;
import static de.jpvee.aoc2021.Parser.pairParser;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Day08 extends Day<Pair<String[], String[]>> {

    private static final Map<Integer, Character> LOOKUP = Map.of(
            119, '0',
            36, '1',
            93, '2',
            109, '3',
            46, '4',
            107, '5',
            123, '6',
            37, '7',
            127, '8',
            111, '9'
    );

    public Day08() {
        super(pairParser(arrayParser("\\s"), arrayParser("\\s"), "\\s\\|\\s"));
    }

    @Override
    public long solveOne() {
        List<Pair<String[], String[]>> data = getData();
        int result = 0;
        for (Pair<String[], String[]> line : data) {
            result += Arrays.stream(line.getSecond()).filter(this::hasUniqueLength).count();
        }
        return result;
    }

    private boolean hasUniqueLength(String str) {
        return str.length() != 5 && str.length() != 6;
    }

    @Override
    public long solveTwo() {
        return getData().stream().mapToLong(line -> solveTwo(line.getFirst(), line.getSecond())).sum();
    }

    private long solveTwo(String[] first, String[] second) {
        char[] cipher = decipher(first);
        String solution = solve(second, cipher);
        return Long.parseLong(solution);
    }

    private char[] decipher(String[] arr) {
        List<String> strings = Arrays.asList(arr);
        strings.sort(Comparator.comparingInt(String::length));

        int one = toInt(strings.get(0));
        int seven = toInt(strings.get(1));
        int four = toInt(strings.get(2));
        int eight = toInt(strings.get(9));

        int diagonal = 0;
        for (int i = 6; i < 9; i++) {
            diagonal |= (eight & ~toInt(strings.get(i)));
        }

        char[] cipher = new char[10];

        int a = seven & ~one;
        cipher[unshift(a)] = 'a';

        int b = four & ~one & ~diagonal;
        cipher[unshift(b)] = 'b';

        int c = one & diagonal;
        cipher[unshift(c)] = 'c';

        int d = four & ~one & diagonal;
        cipher[unshift(d)] = 'd';

        int e = diagonal & ~four;
        cipher[unshift(e)] = 'e';

        int f = one & ~diagonal;
        cipher[unshift(f)] = 'f';

        int g = eight & ~seven & ~four & ~diagonal;
        cipher[unshift(g)] = 'g';

        return cipher;
    }

    private int toInt(String str) {
        int result = 0;
        for (char c : str.toCharArray()) {
            result |= 1 << (c - 'a');
        }
        return result;
    }

    private int unshift(int x) {
        if (x == 0) {
            return -1;
        }
        int pos = 0;
        while ((x & 1) == 0) {
            x = x >> 1;
            pos++;
        }
        return pos;
    }

    private String solve(String[] arr, char[] cipher) {
        StringBuilder result = new StringBuilder();
        for (String str : arr) {
            StringBuilder builder = new StringBuilder();
            for (char c : str.toCharArray()) {
                builder.append(cipher[c - 'a']);
            }
            result.append(LOOKUP.get(toInt(builder.toString())));
        }
        return result.toString();
    }

    public static void main(String[] args) {
        printSolution();
    }

}
