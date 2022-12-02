package eu.rssw.aoc2022.utils;

public class Pair<X, Y> {
  private final X o1;
  private final Y o2;

  public Pair(X o1, Y o2) {
    this.o1 = o1;
    this.o2 = o2;
  }

  public static <A, B> Pair<A, B> of(A x, B y) {
    return new Pair<>(x, y);
  }

  public X getO1() {
    return o1;
  }

  public Y getO2() {
    return o2;
  }
}
