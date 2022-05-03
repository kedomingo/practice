package sudoku;

import java.util.HashSet;
import java.util.Set;

public class Cell {

  private Integer value;
  private Set<Integer> possibleValues = new HashSet<>();

  public Cell() {
  }

  public Cell(int value) {
    this.value = value;
  }

  public Integer getValue() {
    return value;
  }

  public void setValue(Integer value) {
    this.value = value;
    // Setting the final value should clear the possible Values
    possibleValues = new HashSet<>();
  }

  public void addPossibleValue(Integer value) {
    possibleValues.add(value);
  }

  public boolean isPossibleValue(int value) {
    return possibleValues.contains(value);
  }

  public Set<Integer> getPossibleValues() {
    // Always return a copy because this is used in a loop.
    // If we use the same object, the JVM complains when we remove from this set while we are iterating through it
    return new HashSet<>(possibleValues);
  }

  public void removePossibleValue(int value) {
    possibleValues.remove(value);
  }

  public boolean isInCell(int value, boolean includePossibleValues) {
    return (this.value != null && this.value == value) || (includePossibleValues && isPossibleValue(value));
  }

  public Cell copy() {
    var copy = new Cell();
    copy.value = value;
    copy.possibleValues = new HashSet<>(possibleValues);

    return copy;
  }

  public String toString() {
    if (value != null) {
      return String.valueOf(value);
    }

    return possibleValues.toString();
  }
}