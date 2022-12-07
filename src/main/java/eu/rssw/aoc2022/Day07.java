package eu.rssw.aoc2022;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Day07 {
  private static final Logger LOGGER = LoggerFactory.getLogger(Day07.class);

  private final Entry root = new Entry(null, "/");

  public static void main(String[] args) throws IOException {
    LOGGER.info("Start...");
    var main = new Day07();
    main.loadFile("src/main/resources/input07.txt");
    LOGGER.info("Data available...");
    main.part1();
    main.part2();
    LOGGER.info("End...");
  }

  private void getAllDirs(List<Entry> list, Entry entry) {
    if (entry.isDirectory()) list.add(entry);
    for (Entry c : entry.children) {
      getAllDirs(list, c);
    }
  }

  private void part1() {
    List<Entry> list = new ArrayList<>();
    getAllDirs(list, root);
    int sum = list.stream().map(Entry::getSize).filter(it -> it <= 100000).reduce(0, Integer::sum);
    LOGGER.info("Sum: {}", sum);
  }

  private void part2() {
    final int freeReq = root.getSize() - 40000000;
    List<Entry> list = new ArrayList<>();
    getAllDirs(list, root);

    Optional<Integer> e = list.stream().map(Entry::getSize).filter(it -> it > freeReq).sorted().findFirst();
    LOGGER.info("Size: {}", e.get());
  }

  private void loadFile(String fileName) throws IOException {
    var currentEntry = root;
    try (BufferedReader reader = Files.newBufferedReader(Path.of(fileName))) {
      var str = reader.readLine();
      while (str != null) {
        if (str.startsWith("$ ")) {
          if (str.substring(2).startsWith("cd ") && !str.substring(2).startsWith("cd /")) {
            if (str.substring(5).startsWith("..")) {
              currentEntry = currentEntry.parent;
            } else {
              currentEntry = findEntry(currentEntry, str.substring(5));
            }
          }
        } else {
          if (str.startsWith("dir ")) {
            currentEntry.children.add(new Entry(currentEntry, str.substring(4)));
          } else {
            currentEntry.children.add(new Entry(currentEntry, str.substring(str.indexOf(' ') + 1), Integer.parseInt(str.substring(0, str.indexOf(' ')))));
          }
        }
        str = reader.readLine();
      }
    }
  }

  private Entry findEntry(Entry parent, String name) {
    return parent.children.stream().filter(it -> name.equals(it.name)).findFirst().orElse(null);
  }

  private static class Entry {
    private final Entry parent;
    private final String name;
    private final int size;
    private final List<Entry> children = new ArrayList<>();

    public Entry(Entry parent, String name) {
      this(parent, name, -1);
    }

    public Entry(Entry parent, String name, int size) {
      this.parent = parent;
      this.name = name;
      this.size = size;
    }

    public boolean isDirectory() {
      return size == -1;
    }

    public int getSize() {
      return (size == -1 ? 0 : size) + children.stream().map(Entry::getSize).reduce(0, Integer::sum);
    }
  }
}