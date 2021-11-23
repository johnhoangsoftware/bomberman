package uet.gryffindor.util;

public class Pair<K, V> {
  public final K first;
  public final V second;

  private Pair(K first, V second) {
    this.first = first;
    this.second = second;
  }

  public static <K, V> Pair<K, V> of(K first, V second) {
    return new Pair<>(first, second);
  }
}