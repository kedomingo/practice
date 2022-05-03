package sudoku;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Board {

  private Subgroup[][] subgroups = new Subgroup[3][3];

  public Board() {
    // Setup subgroups
    for (var row = 0; row < 3; row++) {
      for (var col = 0; col < 3; col++) {
        subgroups[row][col] = new Subgroup(row * 3, col * 3);
      }
    }
  }

  /**
   * Creates a copy of the board for the purpose of depth search and backtracking
   */
  public Board copy() {
    var copy = new Board();
    for (var row = 0; row < 3; row++) {
      for (var col = 0; col < 3; col++) {
        copy.subgroups[row][col] = subgroups[row][col].copy();
      }
    }

    return copy;
  }

  /**
   * Get the subgroup containing the full board coordinates
   */
  public Subgroup getSubgroupAt(int row, int col) throws RuntimeException {
    validateCoordinaates(row, col);
    var subgroupRow = row / 3;
    var subgroupCol = col / 3;

    return subgroups[subgroupRow][subgroupCol];
  }

  public List<Subgroup> getVerticallyAdjacentSubgroups(int row, int col) throws RuntimeException {
    validateCoordinaates(row, col);
    var subgroupCol = col / 3;
    List<Subgroup> adjacents = new ArrayList<>();
    for (var subgroupRow = 0; subgroupRow < 3; subgroupRow++) {
      adjacents.add(subgroups[subgroupRow][subgroupCol]);
    }

    return adjacents;
  }

  public List<Subgroup> getHorizontallyAdjacentSubgroups(int row, int col) throws RuntimeException {
    validateCoordinaates(row, col);
    var subgroupRow = row / 3;
    List<Subgroup> adjacents = new ArrayList<>();
    for (var subgroupCol = 0; subgroupCol < 3; subgroupCol++) {
      adjacents.add(subgroups[subgroupRow][subgroupCol]);
    }

    return adjacents;
  }

  /**
   * Used when initializing the board
   */
  public void setValueAt(int row, int col, int value) {
    var subgroup = getSubgroupAt(row, col);
    var subgroupRow = row - subgroup.getStartRow();
    var subgroupCol = col - subgroup.getStartCol();

    subgroup.setValueAt(subgroupRow, subgroupCol, value);
  }

  /**
   * Used when rendering the board
   */
  public Integer getValueAt(int row, int col) {
    var subgroup = getSubgroupAt(row, col);
    var subgroupRow = row - subgroup.getStartRow();
    var subgroupCol = col - subgroup.getStartCol();

    return subgroup.getValueAt(subgroupRow, subgroupCol);
  }

  /**
   * Used when rendering the board
   */
  public Set<Integer> getPossibleValuesAt(int row, int col) {
    var subgroup = getSubgroupAt(row, col);
    var subgroupRow = row - subgroup.getStartRow();
    var subgroupCol = col - subgroup.getStartCol();

    return subgroup.getPossibleValuesAt(subgroupRow, subgroupCol);
  }

  public void removePossibleValueAt(int row, int col, int value) {
    var subgroup = getSubgroupAt(row, col);
    var subgroupRow = row - subgroup.getStartRow();
    var subgroupCol = col - subgroup.getStartCol();

    subgroup.removePossibleValueAt(subgroupRow, subgroupCol, value);
  }

  private void validateCoordinaates(int row, int col) {
    if (row < 0 || row > 8) {
      throw new RuntimeException("Invalid value for row: " + row);
    }
    if (col < 0 || col > 8) {
      throw new RuntimeException("Invalid value for row: " + row);
    }
  }

  public boolean isSolved() {
    for (var row = 0; row < 3; row++) {
      for (var col = 0; col < 3; col++) {
        if (!subgroups[row][col].isSolved()) {
          return false;
        }
      }
    }
    return true;
  }

  public String toString() {
    String s = "";
    for (var row = 0; row < 3; row++) {
      for (var col = 0; col < 3; col++) {
        s += subgroups[row][col].toString() + "/";
      }
    }
    return s;
  }
}