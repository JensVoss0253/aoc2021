package de.jpvee.aoc2021;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public abstract class Day<D> {
    
    static void printSolution() {
        try {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            String dayClass = stackTrace[stackTrace.length - 1].getClassName();
            try {
                Day<?> day = (Day<?>) Class.forName(dayClass).getConstructor().newInstance();
                System.out.println("Solution One = " + day.solveOne());
            } catch (UnsupportedOperationException e) {
                // May be ignored
            }
            try {
                Day<?> day = (Day<?>) Class.forName(dayClass).getConstructor().newInstance();
                System.out.println("Solution Two = " + day.solveTwo());
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

    public List<D> getData() {
        return data;
    }

    public abstract long solveOne();
    public abstract long solveTwo();
    
}
