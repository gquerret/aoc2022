package eu.rssw.aoc2022;

import eu.rssw.aoc2022.utils.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day02 {
  private static final Logger LOGGER = LoggerFactory.getLogger(Day02.class);

  private final List<Pair<Type, Type>> list1 = new ArrayList<>();
  private final List<Pair<Type, Outcome>> list2 = new ArrayList<>();

  private static final int WIN_POINTS = 6;
  private static final int DRAW_POINTS = 3;
  private static final int LOSS_POINTS = 0;

  public static void main(String[] args) throws IOException {
    LOGGER.info("Start...");
    var main = new Day02();
    main.loadFile("src/main/resources/input02.txt");
    // main.displayList1();
    // main.displayList2();
    LOGGER.info("Data available...");
    main.part1();
    main.part2();
    LOGGER.info("End...");
  }

  private void part1() {
    int sum = list1.stream().map(it -> it.getO2().battle(it.getO1())).reduce(0, Integer::sum);
    LOGGER.info("Sum 1: {}", sum);
  }

  private void part2() {
    int sum = list2.stream().map(it -> it.getO2().battle(it.getO1())).reduce(0, Integer::sum);
    LOGGER.info("Sum 2: {}", sum);
  }

  private void loadFile(String fileName) throws IOException {
    try (BufferedReader reader = Files.newBufferedReader(Path.of(fileName))) {
      var str = reader.readLine();
      while (str != null) {
        list1.add(Pair.of(Type.fromChar(str.charAt(0)), Type.fromChar(str.charAt(2))));
        list2.add(Pair.of(Type.fromChar(str.charAt(0)), Outcome.fromChar(str.charAt(2))));
        str = reader.readLine();
      }
    }
  }

  private void displayList1() {
    list1.forEach(it -> LOGGER.info("{} {} ==> {}", it.getO1(), it.getO2(), it.getO2().battle(it.getO1())));
  }

  private void displayList2() {
    list2.forEach(it -> LOGGER.info("{} {} ==> {}", it.getO1(), it.getO2(), it.getO2().battle(it.getO1())));
  }

  private static enum Type {
    ROCK, PAPER, SCISSOR;

    public static Type fromChar(char str) {
      return switch (str) {
        case 'A', 'X' -> ROCK;
        case 'B', 'Y' -> PAPER;
        case 'C', 'Z' -> SCISSOR;
        default -> null;
      };
    }

    public Type winAgainst() {
      return switch (this) {
        case ROCK -> SCISSOR;
        case PAPER -> ROCK;
        case SCISSOR -> PAPER;
      };
    }

    public Type loseAgainst() {
      return switch (this) {
        case ROCK -> PAPER;
        case PAPER -> SCISSOR;
        case SCISSOR -> ROCK;
      };
    }

    public int getPoints() {
      return switch (this) {
        case ROCK -> 1;
        case PAPER -> 2;
        case SCISSOR -> 3;
      };
    }

    public int battle(Type other) {
      if (this == other) {
        return DRAW_POINTS + getPoints();
      } else {
        return getPoints() + (winAgainst() == other ? WIN_POINTS : LOSS_POINTS);
      }
    }
  }

  private static enum Outcome {
    WIN, DRAW, LOSS;

    public static Outcome fromChar(char str) {
      return switch (str) {
        case 'X' -> LOSS;
        case 'Y' -> DRAW;
        case 'Z' -> WIN;
        default -> null;
      };
    }

    public int battle(Type t1) {
      if (this == DRAW) {
        return t1.battle(t1);
      } else if (this == LOSS) {
        return t1.winAgainst().battle(t1);
      } else {
        return t1.loseAgainst().battle(t1);
      }
    }
  }
}
