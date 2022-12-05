package eu.rssw.aoc2022;

import eu.rssw.aoc2022.utils.Interval;
import eu.rssw.aoc2022.utils.Pair;
import eu.rssw.aoc2022.utils.Triplet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class Day05 {
  private static final Logger LOGGER = LoggerFactory.getLogger(Day05.class);

  private final Deque<Character>[] deques = new Deque[9];
  private final List<Triplet<Integer, Integer, Integer>> actions = new ArrayList<>();

  public static void main(String[] args) throws IOException {
    LOGGER.info("Start...");
    var main = new Day05();
    main.loadFile("src/main/resources/input05.txt");
    main.displayData();
    main.displayMoves();
    LOGGER.info("Data available...");
    main.part2();
    LOGGER.info("Moved...");
    main.displayData();
    main.displayMessage();
    // main.part2();
    LOGGER.info("End...");
  }

  private void loadFile(String fileName) throws IOException {
    for (int zz = 0; zz < 9; zz++) {
      deques[zz] = new ArrayDeque<>();
    }
    try (BufferedReader reader = Files.newBufferedReader(Path.of(fileName))) {
      var str = reader.readLine();
      while (!str.isBlank()) {
        if (str.charAt(1) != '1') {
          for (int zz = 0; zz < 9; zz++) {
            if ((str.length() > 1 + (zz * 4)) && (str.charAt(1 + (zz * 4)) != ' '))
              deques[zz].addFirst(str.charAt(1 + (zz * 4)));
          }
        }
        str = reader.readLine();
      }
      str = reader.readLine();
      while (str != null) {
        String[] split = str.split(" ");
        actions.add(Triplet.of(Integer.parseInt(split[1]), Integer.parseInt(split[3]), Integer.parseInt(split[5])));
        str = reader.readLine();
      }
    }

  }

  private void part1() {
    actions.stream() //
            .forEach(it -> {
              for (int zz = 0; zz < it.getO1(); zz++) {
                deques[it.getO3() - 1].addLast(deques[it.getO2() - 1].pollLast());
              }
            });
  }

  private void part2() {
    actions.stream() //
            .forEach(it -> {
              Deque<Character> tmp = new ArrayDeque<>();
              for (int zz = 0; zz < it.getO1(); zz++) {
                tmp.addFirst(deques[it.getO2() - 1].pollLast());
              }
              deques[it.getO3() - 1].addAll(tmp);
            });
  }

  private void displayMessage() {
    String str = "";
    for (int zz = 0; zz < 9; zz++) {
      if (!deques[zz].isEmpty())
        str += deques[zz].peekLast();
    }
    LOGGER.info("Message: {}", str);
  }

  private void displayData() {
    for (int zz = 0; zz < 9; zz++) {
      LOGGER.info("{}: {}", zz + 1, deques[zz].stream().map(it -> "[" + it + "]").collect(Collectors.joining(" ")));
    }
  }

  private void displayMoves() {
    actions.stream().forEach(it -> LOGGER.info("Move {} from {} to {}", it.getO1(), it.getO2(), it.getO3()));
  }
}
