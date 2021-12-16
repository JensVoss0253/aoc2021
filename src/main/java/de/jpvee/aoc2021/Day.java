package de.jpvee.aoc2021;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class Day<D> {
    
    static void printSolution() {
        try {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            String dayClass = stackTrace[stackTrace.length - 1].getClassName();
            try {
                Day<?> day = (Day<?>) Class.forName(dayClass).getConstructor().newInstance();
                long time = -System.currentTimeMillis();
                long solution = day.solveOne();
                time += System.currentTimeMillis();
                System.out.printf("Solution One = %d (calculated in %d milliseconds)%n", solution, time);
            } catch (UnsupportedOperationException e) {
                // May be ignored
            }
            try {
                Day<?> day = (Day<?>) Class.forName(dayClass).getConstructor().newInstance();
                long time = -System.currentTimeMillis();
                long solution = day.solveTwo();
                time += System.currentTimeMillis();
                System.out.printf("Solution Two = %d (calculated in %d milliseconds)%n", solution, time);
            } catch (UnsupportedOperationException e) {
                // May be ignored
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private final List<D> data;

    protected Day(Parser<D> parser) {
        try {
            URL resource = this.getClass().getResource(this.getClass().getSimpleName() + ".txt");
            List<String> lines = Files.readAllLines(Paths.get(resource.toURI()));
            data = lines.stream().map(parser::parse).toList();
        } catch (IOException | URISyntaxException e) {
            throw new IllegalStateException(e);
        }
    }

    protected Day(String inputPath, Parser<D> parser) {
        if (inputPath != null) {
            try {
                URL resource = this.getClass().getResource(inputPath);
                List<String> lines = Files.readAllLines(Paths.get(resource.toURI()));
                data = lines.stream().map(parser::parse).toList();
            } catch (IOException | URISyntaxException e) {
                throw new IllegalStateException(e);
            }
        } else {
            data = new ArrayList<>();
        }
    }

    public List<D> getData() {
        return data;
    }

    public abstract long solveOne();
    public abstract long solveTwo();
    
}
