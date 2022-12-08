package eu.rssw.aoc2022;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day08 {
  private static final int SZ = 99;
  private static final Logger LOGGER = LoggerFactory.getLogger(Day08.class);
  private final int[][] square = new int[SZ][SZ];

  private final int[][] left = new int[SZ][SZ];
  private final int[][] right = new int[SZ][SZ];
  private final int[][] top = new int[SZ][SZ];
  private final int[][] bottom = new int[SZ][SZ];

  private final int[][] scenic = new int[SZ][SZ];

  public static void main(String[] args) throws IOException {
    LOGGER.info("Start...");
    var main = new Day08();
    main.loadFile("src/main/resources/input08.txt");
    LOGGER.info("Data available...");
    main.part1();
    // main.displayData();
    main.part2();
    LOGGER.info("End...");
  }

  private void part1() {
    computeViews();
    int count = 0;
    for (int yy = 0; yy < SZ; yy++) {
      for (int xx = 0; xx < SZ; xx++) {
        if ((left[xx][yy] >= 0) || (top[xx][yy] >= 0) || (right[xx][yy] >= 0) || (bottom[xx][yy] >= 0))
          count++;
      }
    }
    LOGGER.info("Sum: {}", count);
  }

  private void part2() {
    computeScenic();
    int high = 0;
    for (int yy = 1; yy < SZ - 1; yy++) {
      for (int xx = 1; xx < SZ - 1; xx++) {
        if (scenic[xx][yy] > high)
          high = scenic[xx][yy];
      }
    }
    LOGGER.info("Highest value: {}", high);
  }
  private void computeViews() {
    for (int yy = 0; yy < SZ; yy++) {
      left[yy][0] = square[yy][0];
      top[0][yy] = square[0][yy];
      right[yy][SZ - 1] = square[yy][SZ - 1];
      bottom[SZ - 1][yy] = square[SZ - 1][yy];
      int vl = left[yy][0];
      int vt = top[0][yy];
      int vr = right[yy][SZ - 1];
      int vb = bottom[SZ - 1][yy];
      for (int xx = 1; xx < SZ; xx++) {
        if (square[yy][xx] > vl) {
          left[yy][xx] = square[yy][xx];
          vl = square[yy][xx];
        } else {
          left[yy][xx] = -1;
        }
        if (square[xx][yy] > vt) {
          top[xx][yy] = square[xx][yy];
          vt = top[xx][yy];
        } else {
          top[xx][yy] = -1;
        }
        if (square[yy][SZ - xx - 1] > vr) {
          right[yy][SZ - 1 - xx] = square[yy][SZ - xx - 1];
          vr = right[yy][SZ - 1 - xx];
        } else {
          right[yy][SZ - 1 - xx] = -1;
        }
        if (square[SZ - xx - 1][yy] > vb) {
          bottom[SZ - 1 - xx][yy] = square[SZ - xx - 1][yy];
          vb = bottom[SZ - 1 - xx][yy];
        } else {
          bottom[SZ - 1 - xx][yy] = -1;
        }
      }
    }
  }
  private void computeScenic() {
    for (int yy = 1; yy < SZ - 1; yy++) {
      for (int xx = 1; xx < SZ - 1; xx++) {
        computeScenicSub(xx, yy);
      }
    }
  }
  private void computeScenicSub(int xx, int yy) {
    int left = 0, right = 0, top = 0, bottom = 0;
    for (int zz = xx - 1; zz >= 0; zz--) {
      left++;
      if (square[yy][zz] >= square[yy][xx])
        break;
    }
    for (int zz = xx + 1; zz < SZ; zz++) {
      right++;
      if (square[yy][zz] >= square[yy][xx])
        break;
    }
    for (int zz = yy - 1; zz >= 0; zz--) {
      top++;
      if (square[zz][xx] >= square[yy][xx])
        break;
    }
    for (int zz = yy + 1; zz < SZ; zz++) {
      bottom++;
      if (square[zz][xx] >= square[yy][xx])
        break;
    }
    scenic[yy][xx] = left * right * bottom * top;
  }

  private void displayData() {
    LOGGER.info("SQUARE");
    displaySquare(square);
    /* LOGGER.info("LEFT");
    displaySquare(left);
    LOGGER.info("TOP");
    displaySquare(top);
    LOGGER.info("RIGHT");
    displaySquare(right);
    LOGGER.info("BOTTOM");
    displaySquare(bottom); */
    LOGGER.info("SCENIC");
    displaySquare(scenic);
  }

  private void displaySquare(int[][] square) {
    for (int yy = 0; yy < SZ; yy++) {
      var str = "";
      for (int xx = 0; xx < SZ; xx++) {
        str += (square[yy][xx] == -1 ? " " : square[yy][xx]) + " ";
      }
      LOGGER.info("{}", str);
    }
  }

  private void loadFile(String fileName) throws IOException {
    int zz = 0;
    try (BufferedReader reader = Files.newBufferedReader(Path.of(fileName))) {
      var str = reader.readLine();
      while (str != null) {
        for (int xx = 0; xx < SZ; xx++) {
          square[zz][xx] = str.charAt(xx) - 48;
        }
        str = reader.readLine();
        zz++;
      }
    }
  }
}
