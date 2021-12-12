package de.jpvee.aoc2021;

import java.util.*;

public class Day12 extends Day<String> {

    static class Node {

        final String name;
        final List<Node> neighbors = new ArrayList<>();

        Node(String name) {
            this.name = name;
        }

        boolean isLarge() {
            return Character.isUpperCase(name.charAt(0));
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private final Map<String, Node> nodes = new HashMap<>();

    public Day12() {
        super(Parser.STRING);
        init();
    }

    public Day12(String inputPath) {
        super(inputPath, Parser.STRING);
        init();
    }

    private void init() {
        List<String> data = getData();
        for (String path : data) {
            String[] parts = path.split("-");
            Node first = nodes.computeIfAbsent(parts[0], Node::new);
            Node second = nodes.computeIfAbsent(parts[1], Node::new);
            first.neighbors.add(second);
            second.neighbors.add(first);
        }
    }

    @Override
    public long solveOne() {
        return solve(0);
    }

    @Override
    public long solveTwo() {
        return solve(1);
    }

    private long solve(int repetitions) {
        List<List<Node>> paths = new ArrayList<>();
        LinkedList<Node> start = new LinkedList<>();
        start.add(nodes.get("start"));
        collectPaths(start, paths, repetitions);
        return paths.size();
    }

    private void collectPaths(LinkedList<Node> path, List<List<Node>> result, int repetitions) {
        Node current = path.getLast();
        if ("end".equals(current.name)) {
            result.add(path);
            return;
        }
        int rep;
        nextNeighbor:
        for (Node neighbor : current.neighbors) {
            rep = repetitions;
            if (!neighbor.isLarge()) {
                for (Node node : path) {
                    if (neighbor.equals(node)) {
                        if (rep == 0 || "start".equals(neighbor.name)) {
                            continue nextNeighbor;
                        } else {
                            rep--;
                        }
                    }
                }
            }
            LinkedList<Node> newPath = new LinkedList<>(path);
            newPath.add(neighbor);
            collectPaths(newPath, result, rep);
        }
    }

    public static void main(String[] args) {
        printSolution();
    }

}
