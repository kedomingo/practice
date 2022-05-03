package sudoku;

import java.util.Set;

public class Subgroup {

  private final int startRow;
  private final int startCol;

  private Cell[][] submatrix = new Cell[3][3];

  public Subgroup(int startRow, int startCol) {
    this.startRow = startRow;
    this.startCol = startCol;

    // Setup cells
    for (var row = 0; row < 3; row++) {
      for (var col = 0; col < 3; col++) {
        submatrix[row][col] = new Cell();
      }
    }
  }

  /**
   * Creates a copy of the subgroup for the purpose of depth search and backtracking.
   */
  public Subgroup copy() {
    var copy = new Subgroup(startRow, startCol);
    for (var row = 0; row < 3; row++) {
      for (var col = 0; col < 3; col++) {
        copy.submatrix[row][col] = submatrix[row][col].copy();
      }
    }

    return copy;
  }

  public boolean contains(int value) {
    return contains(-1, -1, value, false);
  }

  public boolean contains(int exceptRow, int exceptCol, int value, boolean includePossibleValues) {
    if (includePossibleValues && value == 3) {
      var a = "breakpoint";
    }
    for (var row = 0; row < 3; row++) {
      for (var col = 0; col < 3; col++) {
        if (exceptRow >= 0 && row == exceptRow && exceptCol >= 0 && exceptCol == col) {
          continue;
        }
        var cell = submatrix[row][col];
        if (cell.isInCell(value, includePossibleValues)) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean isValueInCol(int exceptRow, int col, int value, boolean includePossibleValues) {
    for (var row = 0; row < 3; row++) {
      if (exceptRow >= 0 && row == exceptRow) {
        continue;
      }
      var cell = submatrix[row][col];
      if ((cell.getValue() != null && cell.getValue() == value) || cell.isInCell(value, includePossibleValues)) {
        return true;
      }
    }
    return false;
  }

  public boolean isValueInRow(int row, int exceptCol, int value, boolean includePossibleValues) {
    for (var col = 0; col < 3; col++) {
      if (exceptCol >= 0 && col == exceptCol) {
        continue;
      }
      var cell = submatrix[row][col];
      if ((cell.getValue() != null && cell.getValue() == value) || cell.isInCell(value, includePossibleValues)) {
        return true;
      }
    }
    return false;
  }

  public void setValueAt(int row, int col, int value) throws RuntimeException {
    validateCoordinates(row, col);

    var cell = submatrix[row][col];
    cell.setValue(value);
  }

  public Integer getValueAt(int row, int col) throws RuntimeException {
    validateCoordinates(row, col);

    var cell = submatrix[row][col];
    return cell.getValue();
  }

  public Set<Integer> getPossibleValuesAt(int row, int col) throws RuntimeException {
    validateCoordinates(row, col);

    var cell = submatrix[row][col];
    return cell.getPossibleValues();
  }

  public void removePossibleValueAt(int row, int col, int value) throws RuntimeException {
    validateCoordinates(row, col);

    var cell = submatrix[row][col];
    cell.removePossibleValue(value);
  }

  /**
   * Removes the possible value from all cells in the subgroup.
   */
  public void removePossibleValue(int value) {
    removePossibleValue(-1, -1, value);
  }

  public void removePossibleValueFromRow(int row, int value) {
    removePossibleValue(row, -1, value);
  }

  public void removePossibleValueFromCol(int col, int value) {
    removePossibleValue(-1, col, value);
  }

  public void removePossibleValue(int onlyInRow, int onlyInCol, int value) {
    for (var row = 0; row < 3; row++) {
      if (onlyInRow >= 0 && row != onlyInRow) {
        continue;
      }
      for (var col = 0; col < 3; col++) {
        if (onlyInCol >= 0 && col != onlyInCol) {
          continue;
        }
        submatrix[row][col].removePossibleValue(value);
      }
    }
  }

  public boolean isPossibleValueAt(int row, int col, int value) throws RuntimeException {
    validateCoordinates(row, col);

    var cell = submatrix[row][col];
    return cell.isPossibleValue(value);
  }

  public void addPossibleValueAt(int row, int col, int value) throws RuntimeException {
    validateCoordinates(row, col);

    var cell = submatrix[row][col];
    cell.addPossibleValue(value);
  }

  private void validateCoordinates(int row, int col) throws RuntimeException {
    if (row < 0 || row > 2) {
      throw new RuntimeException("Invalid value for row: " + row);
    }
    if (col < 0 || col > 2) {
      throw new RuntimeException("Invalid value for col: " + col);
    }
  }

  public int getStartRow() {
    return startRow;
  }

  public int getStartCol() {
    return startCol;
  }

  public String toString() {
    String s = "";
    for (var row = 0; row < 3; row++) {
      for (var col = 0; col < 3; col++) {
        s += submatrix[row][col].toString() + "-";
      }
    }
    return s;
  }
}