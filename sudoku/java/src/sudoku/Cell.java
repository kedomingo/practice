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
  }

  public void addPossibleValue(Integer value) {
    possibleValues.add(value);
  }

  public boolean isPossibleValue(int value) {
    return possibleValues.contains(value);
  }

  public Set<Integer> getPossibleValues() {
    return possibleValues;
  }

  public boolean isInCell(int value) {
    return this.value == value || isPossibleValue(value);
  }
}