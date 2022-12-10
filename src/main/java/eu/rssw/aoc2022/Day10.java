package eu.rssw.aoc2022;

import eu.rssw.aoc2022.utils.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Day10 {
  private static final Logger LOGGER = LoggerFactory.getLogger(Day10.class);

  private final List<Pair<Action, Integer>> actions = new ArrayList<>();
  private final List<Integer> result = new ArrayList<>();

  public static void main(String[] args) throws IOException {
    LOGGER.info("Start... ");
    var main = new Day10();
    main.loadFile("src/main/resources/input10.txt");
    LOGGER.info("Data available...");
    main.part1();
    main.part2();
    LOGGER.info("End...");
  }

  private void part1() {
    int lastValue = 1;
    result.add(lastValue);
    for (Pair<Action, Integer> pair : actions) {
      if (pair.getO1() == Action.NOOP)
        result.add(lastValue);
      else {
        result.add(lastValue);
        lastValue += pair.getO2();
        result.add(lastValue);
      }
    }

    int zz = 20;
    int sum = 0;
    while (zz < 230) {
      LOGGER.info("{}th cycle: {} - Result {}", zz, result.get(zz - 1), result.get(zz - 1) * zz);
      sum += result.get(zz - 1) * zz;
      zz += 40;
    }
    LOGGER.info("Sum: {}", sum);
  }

  private void part2() {
    String line = "";
    for (int cycle = 0; cycle < 240; cycle++) {
      // LOGGER.info("Cycle {} - Sprite is between {} and {}", (cycle % 40), result.get(cycle ) - 1, result.get(cycle )+1);
      line += ((cycle % 40) >= result.get(cycle) - 1) && ((cycle % 40) <= result.get(cycle) + 1) ? '#' : '.';
      if ((cycle % 40) == 39) {
        LOGGER.info("Line {}", line);
        line = "";
      }
    }
  }

  private void loadFile(String fileName) throws IOException {
    try (BufferedReader reader = Files.newBufferedReader(Path.of(fileName))) {
      var str = reader.readLine();
      while (str != null) {
        if (str.startsWith("noop"))
          actions.add(Pair.of(Action.NOOP, 0));
        else
          actions.add(Pair.of(Action.fromString(str.substring(0, str.indexOf(' '))), Integer.parseInt(str.substring(str.indexOf(' ') + 1))));
        str = reader.readLine();
      }
    }
  }

  private enum Action {
    NOOP, ADDX;

    public static Action fromString(String str) {
      if ("noop".equalsIgnoreCase(str)) return NOOP;
      else if ("addx".equalsIgnoreCase(str)) return ADDX;
      else return null;
    }
  }
}
