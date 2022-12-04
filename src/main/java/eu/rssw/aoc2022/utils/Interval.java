package eu.rssw.aoc2022.utils;

public class Interval {
  private final int o1;
  private final int o2;

  public Interval(int o1, int o2) {
    this.o1 = o1;
    this.o2 = o2;
  }

  public static Interval of(int x, int y) {
    return new Interval(x, y);
  }

  public static Interval of(String str) {
    String[] sp = str.split("-");
    return new Interval(Integer.parseInt(sp[0]), Integer.parseInt(sp[1]));
  }

  public int getMin() {
    return o1;
  }

  public int getMax() {
    return o2;
  }

  public boolean fullyContains(Interval other) {
    return (o1 <= other.o1) && (o2 >= other.o2);
  }
  public boolean overlap(Interval other) {
    int min1 = o1 > other.getMin() ? o1 : other.getMin();
    int max1 = o2 < other.getMax() ? o2 : other.getMax();
    return min1 <= max1;
  }

  @Override
  public String toString() {
    return o1 + "-" + o2;
  }
}
