package sudoku.service;

import sudoku.Board;

public class BoardResolver {

  private BoardAnalyzer analyzer;

  public BoardResolver(BoardAnalyzer analyzer) {
    this.analyzer = analyzer;
  }

  public void resolveSinglePossibleValues(Board board) {
    for (var row = 0; row < 9; row++) {
      for (var col = 0; col < 9; col++) {

        var possibleValues = board.getPossibleValuesAt(row, col);
        if (possibleValues.size() == 1) {
          Integer v = possibleValues.stream().findFirst().get();

          System.out.println("Found " + v + " is alone in " + row + "," + col + ". Setting it as value");

          board.setValueAt(row, col, v);

          removePossibleValueFromSubgroup(board, row, col, v);
          removePossibleValueFromRow(board, row, col, v, false);
          removePossibleValueFromCol(board, row, col, v, false);
        }
      }
    }
  }

  public void resolveCellUniqueness(Board board) {
    for (var row = 0; row < 9; row++) {
      for (var col = 0; col < 9; col++) {
        for (var v : board.getPossibleValuesAt(row, col)) {

          if (!analyzer.isValueInSubgroup(board, row, col, v, true)) {
            System.out.println(v + " at " + row + "," + col + " is unique in subgroup.");
          } else if (!analyzer.isValueInRow(board, row, col, v, true)) {
            System.out.println(v + " at " + row + "," + col + " is unique in row.");
          } else if (!analyzer.isValueInCol(board, row, col, v, true)) {
            System.out.println(v + " at " + row + "," + col + " is unique in column.");
          }

          if (!analyzer.isValueInSubgroup(board, row, col, v, true) || !analyzer.isValueInRow(board, row, col, v, true)
              || !analyzer.isValueInCol(board, row, col, v, true)) {
            board.setValueAt(row, col, v);

            removePossibleValueFromSubgroup(board, row, col, v);
            removePossibleValueFromRow(board, row, col, v, false);
            removePossibleValueFromCol(board, row, col, v, false);
          }
        }
      }
    }
  }

  public void resolveColumnRestriction(Board board) {
    for (var row = 0; row < 9; row++) {
      for (var col = 0; col < 9; col++) {
        var subgroup = board.getSubgroupAt(row, col);
        for (var v : board.getPossibleValuesAt(row, col)) {
          var columnsOfPossibleValue = subgroup.getColumnsOfPossibleValue(v);
          if (columnsOfPossibleValue.size() == 1) {
            System.out.printf("Possible value %d at %d,%d is only used in column %d%n", v, row, col, col);
            removePossibleValueFromCol(board, row, col, v, true);
          }
        }
      }
    }
  }

  public void resolveRowRestriction(Board board) {
    for (var row = 0; row < 9; row++) {
      for (var col = 0; col < 9; col++) {
        var subgroup = board.getSubgroupAt(row, col);
        for (var v : board.getPossibleValuesAt(row, col)) {
          var rowsOfPossibleValue = subgroup.getRowsOfPossibleValue(v);
          if (rowsOfPossibleValue.size() == 1) {
            System.out.printf("Possible value %d at %d,%d is only used in row %d%n", v, row, col, row);
            removePossibleValueFromRow(board, row, col, v, true);
          }
        }
      }
    }
  }

  private void removePossibleValueFromSubgroup(Board board, int row, int col, int value) {
    var subgroup = board.getSubgroupAt(row, col);

    System.out.println(
        "Removing " + value + " from subgroup at " + subgroup.getStartRow() + "," + subgroup.getStartCol());
    subgroup.removePossibleValue(value);
  }

  private void removePossibleValueFromRow(Board board, int row, int col, int value, boolean skipCurrent) {
    var adjacentSubgroups = board.getHorizontallyAdjacentSubgroups(row, col);
    var currentSubgroup = board.getSubgroupAt(row, col);
    for (var adjacentSubgroup : adjacentSubgroups) {
      if (skipCurrent && (adjacentSubgroup.getStartRow() == currentSubgroup.getStartRow()
          && adjacentSubgroup.getStartCol() == currentSubgroup.getStartCol())) {
        continue;
      }
      System.out.println("Removing " + value + " from subgroup at " + adjacentSubgroup.getStartRow() + ","
          + adjacentSubgroup.getStartCol() + " at row " + (row - adjacentSubgroup.getStartRow()));
      adjacentSubgroup.removePossibleValueFromRow(row - adjacentSubgroup.getStartRow(), value);
    }
  }

  private void removePossibleValueFromCol(Board board, int row, int col, int value, boolean skipCurrent) {
    var adjacentSubgroups = board.getVerticallyAdjacentSubgroups(row, col);
    var currentSubgroup = board.getSubgroupAt(row, col);
    for (var adjacentSubgroup : adjacentSubgroups) {
      if (skipCurrent && (adjacentSubgroup.getStartRow() == currentSubgroup.getStartRow()
          && adjacentSubgroup.getStartCol() == currentSubgroup.getStartCol())) {
        continue;
      }
      System.out.println("Removing " + value + " from subgroup at " + adjacentSubgroup.getStartRow() + ","
          + adjacentSubgroup.getStartCol() + " at col " + (col - adjacentSubgroup.getStartCol()));
      adjacentSubgroup.removePossibleValueFromCol(col - adjacentSubgroup.getStartCol(), value);
    }
  }
}