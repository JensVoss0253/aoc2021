package de.jpvee.aoc2021;

import java.util.ArrayList;
import java.util.List;

public class Day03 extends Day<String> {

    enum Rating {
        OXYGEN,
        CO2
    }

    static List<String> filter(List<String> input, int pos, Rating rating) {
        List<String>[] result = new List[] { new ArrayList<>(), new ArrayList<>() };
        input.forEach(str -> {
            int idx = str.charAt(pos) - '0';
            result[idx].add(str);
        });
        return switch (rating) {
            case OXYGEN -> result[1].size() >= result[0].size() ? result[1] : result[0];
            case CO2 -> result[1].size() >= result[0].size() ? result[0] : result[1];
        };
    }
    public Day03() {
        super(Parser.STRING);
    }

    @Override
    public long solveOne() {
        List<String> data = getData();
        int len = data.get(0).length();
        String gamma = "";
        String epsilon = "";
        for (int i = 0; i < len; i++) {
            long[] count = new long[2];
            for (String number : data) {
                count[number.charAt(i) - '0']++;
            }
            gamma += (count[0] > count[1] ? "0" : "1");
            epsilon += (count[0] < count[1] ? "0" : "1");
        }
        return Long.parseLong(gamma, 2) * Long.parseLong(epsilon, 2);
    }

    @Override
    public long solveTwo() {
        long[] result = new long[2];
        for (Rating rating : Rating.values()) {
            List<String> data = getData();
            int len = data.get(0).length();
            for (int i = 0; i < len; i++) {
                if (data.size() == 1) {
                    break;
                }
                data = filter(data, i, rating);
            }
            result[rating.ordinal()] = Long.parseLong(data.get(0), 2);
        }
        return result[0] * result[1];
    }

    public static void main(String[] args) {
        printSolution();
    }

}
