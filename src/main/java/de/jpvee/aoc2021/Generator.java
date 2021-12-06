package de.jpvee.aoc2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Arrays;

public class Generator {

    enum Source {

        CLASS("main/java", ".java") {
            @Override
            void writeContent(Path path, int day) throws IOException {
                String content = """
                        package de.jpvee.aoc2021;
                                                
                        public class Day%1$02d extends Day<String> {
                                                
                            public Day%1$02d() {
                                super(Parser.STRING);
                            }
                        
                            @Override
                            public long solveOne() {
                                throw new UnsupportedOperationException();
                            }
                                            
                            @Override
                            public long solveTwo() {
                                throw new UnsupportedOperationException();
                            }
                        
                            public static void main(String[] args) {
                                printSolution();
                            }
                                                
                        }
                        """.formatted(day);
                Files.writeString(path, content);
            }
        },
        INPUT("main/resources", ".txt") {
            @Override
            void writeContent(Path path, int day) throws IOException {
                URL url = new URL("https://adventofcode.com/2021/day/" + day + "/input");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Cookie", "session=" + session);
                InputStream inputStream = connection.getInputStream();
                Files.copy(inputStream, path);
            }
        },
        TEST("test/java", "Test.java") {
            @Override
            void writeContent(Path path, int day) throws IOException {
                String content = """
                        package de.jpvee.aoc2021;
                                                
                        class Day%1$02dTest extends DayTest {
                                                
                            Day%1$02dTest() {
                                super(0L, 0L);
                            }
                                                
                        }
                        """.formatted(day);
                Files.writeString(path, content);
            }
        },
        TEST_INPUT("test/resources", ".txt") {
            @Override
            void writeContent(Path path, int day) throws IOException {
                boolean input = false;
                try (InputStream inputStream = new URL("https://adventofcode.com/2021/day/" + day).openStream();
                        InputStreamReader reader = new InputStreamReader(inputStream);
                        BufferedReader lines = new BufferedReader(reader)) {
                    for (String line = lines.readLine(); line != null; line = lines.readLine()) {
                        line = line.trim();
                        if (line.startsWith("<pre><code>") && line.endsWith("</code></pre>")) {
                            Files.writeString(path, line.substring(11, line.length() - 13));
                            break;
                        } else if (line.startsWith("<pre><code>")) {
                            Files.writeString(path, line.substring(11));
                            input = true;
                        } else if (line.startsWith("</code></pre>")) {
                            break;
                        } else if (input) {
                            Files.writeString(path, "\n" + line, StandardOpenOption.APPEND);
                        }
                    }
                }
            }
        };

        private final String path;
        private final String ext;

        Source(String path, String ext) {
            this.path = path;
            this.ext = ext;
        }

        void generate(int day) {
            try {
                String name = "Day%02d".formatted(day);
                File file = getFile(name);
                if (file.exists()) {
                    System.err.println("File " + file + " already exists.");
                } else {
                    System.out.println("Writing file " + file + "...");
                    writeContent(Paths.get(file.toURI()), day);
                    System.out.println("... done writing file " + file);
                }
            } catch (URISyntaxException | IOException e) {
                throw new IllegalStateException(e);
            }
        }

        abstract void writeContent(Path path, int day) throws IOException;

        private File getFile(String name) throws URISyntaxException, MalformedURLException {
            URL url = Generator.class.getResource("Generator.class");
            url = url.toURI().resolve("../../../../../src/" + path + "/de/jpvee/aoc2021/" + name + ext).toURL();
            return new File(url.getFile());
        }

    }

    private static String session;

    private final int day;

    public Generator(int day) {
        this.day = day;
    }

    public void run() {
        Arrays.stream(Source.values()).forEach(source -> source.generate(day));
    }

    public static void main(String[] args) {
        session = args[0];
        LocalDateTime localDateTime =  LocalDateTime.now();
        int max = localDateTime.get(ChronoField.DAY_OF_MONTH);
        if (localDateTime.get(ChronoField.HOUR_OF_DAY) < 6) {
            max--;
        }
        if (max > 25) {
            max = 25;
        }
        for (int d = 1; d <= max; d++) {
            Generator generator = new Generator(d);
            generator.run();
        }
    }

}
