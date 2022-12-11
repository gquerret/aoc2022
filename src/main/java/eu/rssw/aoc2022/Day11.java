package eu.rssw.aoc2022;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day11 {
  private static final Logger LOGGER = LoggerFactory.getLogger(Day11.class);

  private List<Monkey> monkeys = new ArrayList<>();
  private int mult = 1;

  public static void main(String[] args) throws IOException {
    LOGGER.info("Start... ");
    var main = new Day11();
    main.loadFile("src/main/resources/input11.txt");
    LOGGER.info("Data available...");
    main.part1();
    LOGGER.info("Part 1 done");
    main.monkeys.clear();
    main.mult = 1;
    main.loadFile("src/main/resources/input11.txt");
    main.part2();
    LOGGER.info("End...");
  }

  private void part1() {
    for (int zz = 1; zz <= 20; zz++) {
      // LOGGER.info("*** ROUND {} ***", zz);
      round(true, zz);
    }

    long result = monkeys.stream().map(it -> it.inspections).sorted((a, b) -> (int) (b - a)).limit(2).reduce(1L, (a, b) -> a * b);
    LOGGER.info("Result: {}", result);
  }

  private void part2() {
    LOGGER.info("Common multiplier: {}", mult);
    for (int zz = 1; zz <= 10000; zz++) {
      round(false, zz);
    }

    long result = monkeys.stream().map(it -> it.inspections).sorted((a, b) -> (int) (b - a)).limit(2).reduce(1L, (a, b) -> a * b);
    LOGGER.info("Result: {}", result);
  }

  private void round(boolean part1, int roundNum) {
    int monkeyNum = 0;
    for (Monkey monkey : monkeys) {
      // LOGGER.info("Monkey {}", monkeyNum);
      while (!monkey.items.isEmpty()) {
        monkey.inspections++;
        Long item = monkey.items.remove(0);
        // LOGGER.info("  Monkey {} inspects an item with a worry level of {} - Result: {}", monkeyNum, item, monkey.operation.apply(item) % commonMult);
        item = monkey.operation.apply(item);
        if (!part1)
          item = item % mult;
        // LOGGER.info("    Worry level now at {}", item);
        if (part1) {
          item = item / 3;
          // LOGGER.info("    Monkey gets bored with item. Worry level is divided by 3 to {}", item);
        }
        boolean condition = monkey.test.test(item);
        // LOGGER.info("    Condition is {}", condition);
        monkeys.get(condition ? monkey.ifTrue : monkey.ifFalse).items.add(item);
        // LOGGER.info("    Item with worry level {} is thrown to monkey {}", item, condition ? monkey.ifTrue : monkey.ifFalse);
      }
      monkeyNum++;
    }

    if ((roundNum == 1) || (roundNum == 20) || ((roundNum % 1000) == 0)) {
      LOGGER.info("*** ROUND {} ***", roundNum);
      monkeyNum = 0;
      for (Monkey monkey : monkeys) {
        LOGGER.info("Monkey {} -- {} inspections -- {}", monkeyNum, monkey.inspections, monkey.items.stream().map(it -> it.toString()).collect(Collectors.joining(", ")));
        monkeyNum++;
      }
    }
  }

  private void loadFile(String fileName) throws IOException {
    try (BufferedReader reader = Files.newBufferedReader(Path.of(fileName))) {
      var str = reader.readLine();
      while (str != null) {
        Monkey monkey = new Monkey();
        monkeys.add(monkey);
        str = reader.readLine();
        for (String s : str.substring(str.indexOf(':') + 1).split(",")) {
          monkey.items.add((long) Integer.parseInt(s.trim()));
        }
        str = reader.readLine();
        if ("old".equals(str.substring(25)))
          monkey.operation = x -> x * x;
        else {
          final int xx = Integer.parseInt(str.substring(25));
          if (str.charAt(23) == '*')
            monkey.operation = x -> x * xx;
          else
            monkey.operation = x -> x + xx;
        }
        str = reader.readLine();
        final int xx2 = Integer.parseInt(str.substring(21));
        mult *= xx2;
        monkey.test = x -> (x % xx2) == 0;
        str = reader.readLine();
        monkey.ifTrue = Integer.parseInt(str.substring(29));
        str = reader.readLine();
        monkey.ifFalse = Integer.parseInt(str.substring(30));
        str = reader.readLine();
        str = reader.readLine();
      }
    }
  }

  private static class Monkey {
    List<Long> items = new ArrayList<>();
    Function<Long, Long> operation;
    Predicate<Long> test;
    int ifTrue;
    int ifFalse;
    long inspections;
  }

}
