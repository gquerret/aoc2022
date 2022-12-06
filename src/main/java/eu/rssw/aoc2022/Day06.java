package eu.rssw.aoc2022;

import eu.rssw.aoc2022.utils.Interval;
import eu.rssw.aoc2022.utils.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day06 {
  private static final Logger LOGGER = LoggerFactory.getLogger(Day06.class);

  private char[] list;

  public static void main(String[] args) throws IOException {
    LOGGER.info("Start...");
    var main = new Day06();
    main.loadFile("src/main/resources/input06.txt");
    LOGGER.info("Data available...");
    main.part1();
    main.part2();
    LOGGER.info("End...");
  }

  private void part1() {
    for (int zz = 3; zz < list.length; zz++) {
      if ((list[zz] != list[zz - 1]) && (list[zz] != list[zz - 2]) && (list[zz] != list[zz - 3])
          && (list[zz - 1] != list[zz - 2]) && (list[zz - 1] != list[zz - 3])
          && (list[zz - 2] != list[zz - 3])) {
        LOGGER.info("First marker: {}", zz + 1);
        return;
      }
    }
  }

  private void part2() {
    int[] count = new int[26];
    for (int zz = 0; zz < 14; zz++) {
      count[list[zz] - 97]++;
    }
    if (allDiff(count)) {
      LOGGER.info("First marker: {}", 14);
      return;
    }
    for (int zz = 14; zz < list.length; zz++) {
      count[list[zz - 14] - 97]--;
      count[list[zz] - 97]++;
      if (allDiff(count)) {
        LOGGER.info("First marker: {}", zz + 1);
        return;
      }
    }
  }

  private boolean allDiff(int[] count) {
    for (int zz = 0; zz < count.length; zz++) {
      if (count[zz] > 1)
        return false;
    }
    return true;
  }

  private void loadFile(String fileName) throws IOException {
    try (BufferedReader reader = Files.newBufferedReader(Path.of(fileName))) {
      var str = reader.readLine();
      list = str.toCharArray();
    }
  }
}
