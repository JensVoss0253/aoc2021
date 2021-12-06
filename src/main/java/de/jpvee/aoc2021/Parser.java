package de.jpvee.aoc2021;

import java.util.Arrays;

public interface Parser<D> {

    D parse(String input);

    Parser<String> STRING = input -> input;
    Parser<Long> DECIMAL = Long::valueOf;
    Parser<Long> BINARY = input -> Long.valueOf(input, 2);
    Parser<int[]> INT_ARRAY = input -> Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();

    static <F, S> Parser<Pair<F, S>> pairParser(Parser<F> first, Parser<S> second) {
        return pairParser(first, second, "\\s");
    }

    static <F, S> Parser<Pair<F, S>> pairParser(Parser<F> first, Parser<S> second, String delim) {
        return input -> {
            String[] split = input.split(delim, 2);
            return new Pair<>(first.parse(split[0]), second.parse(split[1]));
        };
    }

    static <E extends Enum<E>> Parser<E> enumParser(Class<E> enumType) {
        return input -> Enum.valueOf(enumType, input);
    }

}
