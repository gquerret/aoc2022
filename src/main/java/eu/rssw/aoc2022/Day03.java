package eu.rssw.aoc2022;

import eu.rssw.aoc2022.utils.Pair;
import eu.rssw.aoc2022.utils.Triplet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Day03 {
  private static final Logger LOGGER = LoggerFactory.getLogger(Day03.class);

  private final List<String> list1 = new ArrayList<>();
  private final List<Triplet<String, String, String>> list2 = new ArrayList<>();

  private final Function<String, Pair<String, String>> TO_PAIR = it ->
    Pair.of(it.substring(0, it.length() / 2), it.substring(it.length() / 2))
  ;

  private final Function<Pair<String, String>, Character> FIRST_COMMON_CHAR_PAIR = pair -> {
    char[] part1 = pair.getO1().toCharArray();
    char[] part2 = pair.getO2().toCharArray();
    Arrays.sort(part1);
    Arrays.sort(part2);
    int zz1 = 0;
    int zz2 = 0;
    while (true) {
      if (part1[zz1] == part2[zz2]) {
        return part1[zz1];
      } else if (part1[zz1] < part2[zz2]) {
        zz1++;
      } else {
        zz2++;
      }
    }
  };

   private final Function<Triplet<String, String, String>, Character> FIRST_COMMON_CHAR_TRIPLET = triplet -> {
    char[] part1 = triplet.getO1().toCharArray();
    char[] part2 = triplet.getO2().toCharArray();
    char[] part3 = triplet.getO3().toCharArray();
    Arrays.sort(part1);
    Arrays.sort(part2);
    Arrays.sort(part3);

    int zz1 = 0;
    int zz2 = 0;
    int zz3 = 0;
    while (true) {
      if ((part1[zz1] == part2[zz2]) && (part1[zz1] == part3[zz3])) {
        return part1[zz1];
      } else if ((part1[zz1] <= part2[zz2]) && (part1[zz1] <= part3[zz3])) {
        zz1++;
      } else if ((part2[zz2] <= part1[zz1]) && (part2[zz2] <= part3[zz3])) {
        zz2++;
      } else {
        zz3++;
      }
    }
  };

  private final Function<Character, Long> PRIORITIZE = c -> c > 97 ? c - 96L : c - 64L + 26L;

  public static void main(String[] args) throws IOException {
    LOGGER.info("Start...");
    var main = new Day03();
    main.loadFile("src/main/resources/input03.txt");
    // main.displayList1();
    // main.displayList2();
    LOGGER.info("Data available...");
    main.part1();
    main.part2();
    LOGGER.info("End...");
  }

  private void part1() {
    long sum = list1.stream() //
        .map(TO_PAIR) //
        .map(FIRST_COMMON_CHAR_PAIR) //
        .map(PRIORITIZE) //
        .reduce(0L, Long::sum);
    LOGGER.info("Sum: {}", sum);
  }

  private void part2() {
    long sum = list2.stream().map(FIRST_COMMON_CHAR_TRIPLET).map(PRIORITIZE).reduce(0L, Long::sum);
    LOGGER.info("Sum 2: {}", sum);
  }

  private void loadFile(String fileName) throws IOException {
    try (BufferedReader reader = Files.newBufferedReader(Path.of(fileName))) {
      var str1 = reader.readLine();
      while (str1 != null) {
        list1.add(str1);
        var str2 = reader.readLine();
        var str3 = reader.readLine();
        list1.add(str2);
        list1.add(str3);
        list2.add(Triplet.of(str1, str2, str3));
        str1 = reader.readLine();
      }
    }
  }

  private void displayList1() {
    list1.forEach(it -> LOGGER.info("Line '{}' => {}", it, PRIORITIZE.apply(FIRST_COMMON_CHAR_PAIR.apply(TO_PAIR.apply(it)))));
  }

  private void displayList2() {
    list2.forEach(it -> LOGGER.info("Lines '{}' '{}' '{}' => {}", it.getO1(), it.getO2(), it.getO3(), PRIORITIZE.apply(FIRST_COMMON_CHAR_TRIPLET.apply(it))));
  }

}
