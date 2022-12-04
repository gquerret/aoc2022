package eu.rssw.aoc2022;

import eu.rssw.aoc2022.utils.Interval;
import eu.rssw.aoc2022.utils.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day04 {
  private static final Logger LOGGER = LoggerFactory.getLogger(Day04.class);

  private final List<Pair<Interval, Interval>> list = new ArrayList<>();

  public static void main(String[] args) throws IOException {
    LOGGER.info("Start...");
    var main = new Day04();
    main.loadFile("src/main/resources/input04.txt");
    // main.displayList1();
    LOGGER.info("Data available...");
    main.part1();
    main.part2();
    LOGGER.info("End...");
  }

  private void loadFile(String fileName) throws IOException {
    try (BufferedReader reader = Files.newBufferedReader(Path.of(fileName))) {
      var str = reader.readLine();
      while (str != null) {
        String[] spl1 = str.split(",");
        list.add(Pair.of(Interval.of(spl1[0]), Interval.of(spl1[1])));
        str = reader.readLine();
      }
    }
  }

  private void part1() {
    long sum = list.stream() //
        .filter(it -> it.getO1().fullyContains(it.getO2()) || it.getO2().fullyContains(it.getO1())) //
        .count();
    LOGGER.info("Sum1: {}", sum);
  }

  private void part2() {
    long sum = list.stream() //
        .filter(it -> it.getO1().overlap(it.getO2())) //
        .count();
    LOGGER.info("Sum2: {}", sum);
  }

  private void displayList1() {
    list.forEach(it -> LOGGER.info("Intervals '{}' and '{}' ==> {}", it.getO1(), it.getO2(), it.getO1().fullyContains(it.getO2()) || it.getO2().fullyContains(it.getO1())));
  }

}
