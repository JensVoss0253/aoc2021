package de.jpvee.aoc2021;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.Map;

public class Day10 extends Day<String> {

    private static final Map<Character, Character> SYMBOLS = Map.of('(', ')', '[', ']', '{', '}', '<', '>');
    private static final Map<Character, Long> SCORES = Map.of(')', 3L, ']', 57L, '}', 1197L, '>', 25137L);
    private static final Map<Character, Long> COMPLETION = Map.of(')', 1L, ']', 2L, '}', 3L, '>', 4L);


    public Day10() {
        super(Parser.STRING);
    }

    @Override
    public long solveOne() {
        long sum = 0;
        List<String> data = getData();
        for (String line : data) {
            Deque<Character> deque = new ArrayDeque<>();
            Character character = isCorrupted(line, deque);
            if (character != null) {
                sum += SCORES.get(character);
            }
        }
        return sum;
    }

    @Override
    public long solveTwo() {
        List<Long> scores = new ArrayList<>();
        List<String> data = getData();
        for (String line : data) {
            Deque<Character> deque = new ArrayDeque<>();
            Character character = isCorrupted(line, deque);
            if (character == null) {
                scores.add(getCompletionScore(deque));
            }
        }
        scores.sort(Comparator.naturalOrder());
        return scores.get(scores.size() / 2);
    }

    private Long getCompletionScore(Deque<Character> stack) {
        long score = 0;
        while (!stack.isEmpty()) {
            score = 5 * score + COMPLETION.get(SYMBOLS.get(stack.pop()));
        }
        return score;
    }

    private Character isCorrupted(String line, Deque<Character> stack) {
        for (char c : line.toCharArray()) {
            if (SYMBOLS.containsKey(c)) {
                stack.push(c);
            } else {
                if (stack.isEmpty()) {
                    return c;
                } else {
                    Character pop = stack.pop();
                    if (SYMBOLS.get(pop) != c) {
                        return c;
                    }
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        printSolution();
    }

}
