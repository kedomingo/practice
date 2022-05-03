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
          removePossibleValueFromRow(board, row, col, v);
          removePossibleValueFromCol(board, row, col, v);
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

          if (!analyzer.isValueInSubgroup(board, row, col, v, true)
              || !analyzer.isValueInRow(board, row, col, v, true)
              || !analyzer.isValueInCol(board, row, col, v, true)) {
            board.setValueAt(row, col, v);

            removePossibleValueFromSubgroup(board, row, col, v);
            removePossibleValueFromRow(board, row, col, v);
            removePossibleValueFromCol(board, row, col, v);
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

  private void removePossibleValueFromRow(Board board, int row, int col, int value) {
    var adjacentSubgroups = board.getHorizontallyAdjacentSubgroups(row, col);
    for (var subgroup : adjacentSubgroups) {
      System.out.println(
          "Removing " + value + " from subgroup at " + subgroup.getStartRow() + "," + subgroup.getStartCol()
              + " at row " + (row - subgroup.getStartRow()));
      subgroup.removePossibleValueFromRow(row - subgroup.getStartRow(), value);
    }
  }

  private void removePossibleValueFromCol(Board board, int row, int col, int value) {
    var adjacentSubgroups = board.getVerticallyAdjacentSubgroups(row, col);
    for (var subgroup : adjacentSubgroups) {
      System.out.println(
          "Removing " + value + " from subgroup at " + subgroup.getStartRow() + "," + subgroup.getStartCol()
              + " at col " + (col - subgroup.getStartCol()));
      subgroup.removePossibleValueFromCol(col - subgroup.getStartCol(), value);
    }
  }
}