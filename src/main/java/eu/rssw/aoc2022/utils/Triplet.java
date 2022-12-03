package eu.rssw.aoc2022.utils;

public class Triplet<X, Y, Z> {
  private final X o1;
  private final Y o2;
  private final Z o3;

  public Triplet(X o1, Y o2, Z o3) {
    this.o1 = o1;
    this.o2 = o2;
    this.o3 = o3;
  }

  public static <A, B, C> Triplet<A, B, C> of(A x, B y, C z) {
    return new Triplet<>(x, y, z);
  }

  public X getO1() {
    return o1;
  }

  public Y getO2() {
    return o2;
  }

  public Z getO3() { return o3; }
}
