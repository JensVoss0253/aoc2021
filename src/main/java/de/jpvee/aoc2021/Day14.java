package de.jpvee.aoc2021;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14 extends Day<String> {

    private final String template;

    private final Map<Character, Map<Character, Character>> insertionRules = new HashMap<>();

    public Day14() {
        super(Parser.STRING);
        List<String> data = getData();
        this.template = data.get(0);
        Pattern pattern = Pattern.compile("([A-Z])([A-Z])\\s*->\\s([A-Z])");
        data.stream().skip(2).forEach(line -> {
            Matcher matcher = pattern.matcher(line);
            if (!matcher.matches()) {
                throw new IllegalStateException();
            }
            Map<Character, Character> map = insertionRules.computeIfAbsent(matcher.group(1).charAt(0),
                    c -> new HashMap<>());
            map.put(matcher.group(2).charAt(0), matcher.group(3).charAt(0));
        });
    }

    @Override
    public long solveOne() {
        return solve(10);
    }

    @Override
    public long solveTwo() {
        return solve(40);
    }

    private long solve(int count) {
        Map<Character, Map<Character, Long>> map = new HashMap<>();
        for (int i = 1; i < template.length(); i++) {
            Map<Character, Long> map1 = map.computeIfAbsent(template.charAt(i - 1), c -> new HashMap<>());
            Long oldValue = map1.getOrDefault(template.charAt(i), 0L);
            map1.put(template.charAt(i), oldValue + 1);
        }
        Map<Character, Map<Character, Long>> polymer = grow(map, count);
        List<Long> counts = polymer.entrySet()
                                   .stream()
                                   .map(entry -> {
                                       int offset = entry.getKey().equals(template.charAt(template.length() - 1)) ?
                                               1 : 0;
                                       return entry.getValue().values().stream().mapToLong(l -> l).sum() + offset;
                                   })
                                   .sorted()
                                   .toList();
        return counts.get(counts.size() - 1) - counts.get(0);
    }

    private Map<Character, Map<Character, Long>> grow(Map<Character, Map<Character, Long>> map, int count) {
        if (count == 0) {
            return map;
        }
        Map<Character, Map<Character, Long>> result = new HashMap<>();
        for (Map.Entry<Character, Map<Character, Long>> entry : map.entrySet()) {
            for (Map.Entry<Character, Long> entry1 : entry.getValue().entrySet()) {
                Character first = entry.getKey();
                Character second = entry1.getKey();
                Character middle = insertionRules.get(first).get(second);
                Map<Character, Long> oldMap = result.computeIfAbsent(first, c -> new HashMap<>());
                Long old = oldMap.getOrDefault(middle, 0L);
                oldMap.put(middle, old + entry1.getValue());
                oldMap = result.computeIfAbsent(middle, c -> new HashMap<>());
                old = oldMap.getOrDefault(second, 0L);
                oldMap.put(second, old + entry1.getValue());
            }
        }
        return grow(result, count - 1);
    }

    public static void main(String[] args) {
        printSolution();
    }

}
