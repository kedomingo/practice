package sudoku;

import java.util.List;
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

  public boolean contains(int value) {
    return contains(value, false);
  }

  public boolean contains(int value, boolean includePossibleValues) {
    for (var row = 0; row < 3; row++) {
      for (var col = 0; col < 3; col++) {
        var cell = submatrix[row][col];
        if (cell == null) {
          throw new RuntimeException("Cell at " + row + ", " + col + " is null");
        }
        if (includePossibleValues && cell.isInCell(value)) {
          return true;
        }
        if (cell.getValue() != null && cell.getValue() == value) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean isValueInCol(int col, int value) {
    return isValueInCol(col, value, false);
  }

  public boolean isValueInCol(int col, int value, boolean includePossibleValues) {
    for (var row = 0; row < 3; row++) {
      var cell = submatrix[row][col];
      if ((cell.getValue() != null && cell.getValue() == value) || (includePossibleValues && cell.isInCell(value))) {
        return true;
      }
    }
    return false;
  }

  public boolean isValueInRow(int row, int value) {
    return isValueInRow(row, value, false);
  }

  public boolean isValueInRow(int row, int value, boolean includePossibleValues) {
    for (var col = 0; col < 3; col++) {
      var cell = submatrix[row][col];
      if ((cell.getValue() != null && cell.getValue() == value) || (includePossibleValues && cell.isInCell(value))) {
        return true;
      }
    }
    return false;
  }

  public void setCellAt(int row, int col, Cell cell) throws RuntimeException {
    validateCoordinates(row, col);

    submatrix[row][col] = cell;
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

  public Cell getCellAt(int row, int col) throws RuntimeException {
    validateCoordinates(row, col);

    return submatrix[row][col];
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
}