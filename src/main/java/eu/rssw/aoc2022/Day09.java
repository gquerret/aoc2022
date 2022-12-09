package eu.rssw.aoc2022;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day09 {
  private static final int SZ = 1000;
  private static final int INIT_POS = (SZ / 2) + ((SZ / 2) * SZ);
  private static final Logger LOGGER = LoggerFactory.getLogger(Day09.class);

  private final List<String> list = new ArrayList<>();
  private final Set<Integer> pos = new HashSet<>();
  private final int[] rope = {INIT_POS, INIT_POS, INIT_POS, INIT_POS, INIT_POS, INIT_POS, INIT_POS, INIT_POS, INIT_POS, INIT_POS};

  private int head = INIT_POS;
  private int tail = INIT_POS;

  public static void main(String[] args) throws IOException {
    LOGGER.info("Start... ");
    var main = new Day09();
    main.loadFile("src/main/resources/input09.txt");
    LOGGER.info("Data available...");
    main.part1();
    main.part2();
    LOGGER.info("End...");
  }

  private void part1() {
    pos.add(tail);
    for (String s : list) {
      int count = Integer.parseInt(s.substring(2));
      for (int zz = 0; zz < count; zz++) {
        int newHead = switch (s.charAt(0)) {
          case 'U' -> head + SZ;
          case 'R' -> head + 1;
          case 'L' -> head - 1;
          case 'D' -> head - SZ;
          default -> 0;
        };
        tail = move(head, newHead, tail);
        head = newHead;
        pos.add(tail);
      }
    }
    LOGGER.info("Count: {}", pos.size());
  }

  private void part2() {
    pos.clear();
    pos.add(INIT_POS);

    for (String s : list) {
      int count = Integer.parseInt(s.substring(2));
      for (int zz = 0; zz < count; zz++) {
        int newHead = switch (s.charAt(0)) {
          case 'U' -> rope[0] + SZ;
          case 'R' -> rope[0] + 1;
          case 'L' -> rope[0] - 1;
          case 'D' -> rope[0] - SZ;
          default -> 0;
        };
        tail = move(rope[0], newHead, rope[1]);
        rope[0] = newHead;

        for (int xx = 1; xx < 9; xx++) {
          int newTail = move(rope[xx], tail, rope[xx + 1]);
          rope[xx] = tail;
          tail = newTail;
        }
        rope[9] = tail;
        pos.add(rope[9]);
      }
    }

    LOGGER.info("Count: {}", pos.size());
  }

  private boolean touch(int head, int tail) {
    if (tail == head)
      return true;
    else if ((tail >= head + SZ - 1) && (tail <= head + SZ + 1))
      return true;
    else if ((tail == head - 1) || (tail == head + 1))
      return true;
    else if ((tail >= head - SZ - 1) && (tail <= head - SZ + 1))
      return true;
    else return false;
  }

  // Return new tail position
  private int move(int head, int newHead, int tail) {
    if ((head == newHead) || (touch(newHead, tail)))
      return tail;
    else {
      int move = newHead - head;
      if (move == 1)
        return newHead - 1;
      else if (move == -1)
        return newHead + 1;
      else if (move == SZ)
        return newHead - SZ;
      else if (move == -SZ)
        return newHead + SZ;
      else {
        if (newHead == tail + 2)
          return tail + 1;
        else if (newHead == tail - 2)
          return tail - 1;
        else if (newHead == tail + SZ + SZ)
          return tail + SZ;
        else if (newHead == tail - SZ - SZ)
          return tail - SZ;
        else return tail + (newHead - head);
      }
    }
  }

  private void displayGrid() {
    for (int yy = SZ - 1; yy >= 0; yy--) {
      String str = "";
      for (int xx = 0; xx < SZ; xx++) {
        if ((head == (yy * SZ) + xx) && (tail == (yy * SZ) + xx))
          str += 'B';
        else if (head == (yy * SZ) + xx)
          str += 'H';
        else if (tail == (yy * SZ) + xx)
          str += 'T';
        else
          str += '.';
      }
      LOGGER.info("{}", str);
    }
  }

  private void displayPath() {
    for (int yy = SZ - 1; yy >= 0; yy--) {
      String str = "";
      for (int xx = 0; xx < SZ; xx++) {
        if (pos.contains((yy * SZ) + xx))
          str += '#';
        else
          str += '.';
      }
      LOGGER.info("{}", str);
    }
  }

  private void displayRope() {
    for (int yy = SZ - 1; yy >= 0; yy--) {
      String str = "";
      for (int xx = 0; xx < SZ; xx++) {
        if (rope[0] == (yy * SZ) + xx) {
          str += 'H';
        } else {
          String c = (yy * SZ) + xx == INIT_POS ? "s" : ".";
          for (int kk = 1; kk < 10; kk++) {
            if (rope[kk] == (yy * SZ) + xx) {
              c = "" + kk;
              break;
            }
          }
          str += c;
        }
      }
      LOGGER.info("{}", str);
    }
    LOGGER.info("------");
  }

  private void loadFile(String fileName) throws IOException {
    try (BufferedReader reader = Files.newBufferedReader(Path.of(fileName))) {
      var str = reader.readLine();
      while (str != null) {
        list.add(str);
        str = reader.readLine();
      }
    }
  }

}
