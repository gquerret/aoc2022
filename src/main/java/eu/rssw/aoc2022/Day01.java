package eu.rssw.aoc2022;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day01 {
  private static final Logger LOGGER = LoggerFactory.getLogger(Day01.class);

  private final List<List<Integer>> list = new ArrayList<>();

  public static void main(String[] args) throws IOException {
    LOGGER.info("Start...");
    var main = new Day01();
    main.loadFile("src/main/resources/input01.txt");
    LOGGER.info("Data available...");
    main.part1();
    main.part2();
    LOGGER.info("End...");
  }

  private void part1() {
    int max = list.stream().map(it -> it.stream().reduce(0, Integer::sum)).max(Integer::compareTo).orElse(0);
    LOGGER.info("Max value: {}", max);
  }

  private void part2() {
    int sum = list.stream().map(it -> it.stream().reduce(0, Integer::sum)).sorted((a, b) -> b - a).limit(3).reduce(0, Integer::sum);
    LOGGER.info("First three: {}", sum);
  }

  private void loadFile(String fileName) throws IOException {
    try (BufferedReader reader = Files.newBufferedReader(Path.of(fileName))) {
      var str = reader.readLine();
      List<Integer> currList = new ArrayList<>();
      while (str != null) {
        if (str.isBlank()) {
          list.add(currList);
          currList = new ArrayList<>();
        } else {
          currList.add(Integer.parseInt(str));
        }
        str = reader.readLine();
      }
      if (!currList.isEmpty())
        list.add(currList);
    }
  }

}
