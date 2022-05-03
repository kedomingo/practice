package sudoku.service;

import sudoku.Board;

public class BoardAnalyzer {

  public boolean isValueInRow(Board board, int row, int value) {
    return isValueInRow(board, row, -1, value, false);
  }

  public boolean isValueInRow(Board board, int row, int col, int value) {
    return isValueInRow(board, row, col, value, false);
  }

  public boolean isValueInRow(Board board, int row, int col, int value, boolean includePossibleValues) {
    var adjacentGroups = board.getHorizontallyAdjacentSubgroups(row, 0);
    for (var subgroup : adjacentGroups) {
      if (subgroup.isValueInRow(row - subgroup.getStartRow(), col - subgroup.getStartCol(), value, includePossibleValues)) {
        return true;
      }
    }
    return false;
  }

  public boolean isValueInCol(Board board, int col, int value) {
    return isValueInCol(board, -1, col, value, false);
  }

  public boolean isValueInCol(Board board, int row, int col, int value) {
    return isValueInCol(board, row, col, value, false);
  }

  public boolean isValueInCol(Board board, int row, int col, int value, boolean includePossibleValues) {
    var adjacentGroups = board.getVerticallyAdjacentSubgroups(0, col);
    for (var subgroup : adjacentGroups) {
      if (subgroup.isValueInCol(row - subgroup.getStartRow(), col - subgroup.getStartCol(), value, includePossibleValues)) {
        return true;
      }
    }
    return false;
  }

  public boolean isValueInSubgroup(Board board, int row, int col, int value, boolean includePossibleValues) {
    var subgroup = board.getSubgroupAt(row, col);
    return subgroup.contains(row - subgroup.getStartRow(), col - subgroup.getStartCol(), value, includePossibleValues);
  }
}