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

  public void initializeBoardWithPossibleValues() {
    for (var row = 0; row < 9; row++) {
      for (var col = 0; col < 9; col++) {
        if (row == 0 && col == 4) {
          var i = 1;
        }
        var subgroup = getSubgroupAt(row, col);
        var subgroupRow = row - subgroup.getStartRow();
        var subgroupCol = col - subgroup.getStartCol();
        var value = subgroup.getValueAt(subgroupRow, subgroupCol);
        if (value == null) {
          for (var v = 1; v <= 9; v++) {
            if (!subgroup.contains(v) && !isValueInRow(row, v) && !isValueInCol(col, v)) {
              subgroup.addPossibleValueAt(subgroupRow, subgroupCol, v);
            }
          }
        }
      }
    }
  }

  public boolean isValueInRow(int row, int value) {
    return isValueInRow(row, value, false);
  }

  public boolean isValueInRow(int row, int value, boolean includePossibleValues) {
    var adjacentGroups = getHorizontallyAdjacentSubgroups(row, 0);
    for (var subgroup : adjacentGroups) {
      if (subgroup.isValueInRow(row - subgroup.getStartRow(), value, includePossibleValues)) {
        return true;
      }
    }
    return false;
  }

  public boolean isValueInCol(int col, int value) {
    return isValueInCol(col, value, false);
  }

  public boolean isValueInCol(int col, int value, boolean includePossibleValues) {
    var adjacentGroups = getVerticallyAdjacentSubgroups(0, col);
    for (var subgroup : adjacentGroups) {
      if (subgroup.isValueInCol(col - subgroup.getStartCol(), value, includePossibleValues)) {
        return true;
      }
    }
    return false;
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

  private void validateCoordinaates(int row, int col) {
    if (row < 0 || row > 8) {
      throw new RuntimeException("Invalid value for row: " + row);
    }
    if (col < 0 || col > 8) {
      throw new RuntimeException("Invalid value for row: " + row);
    }
  }
}