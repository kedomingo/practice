package sudoku.service;

import sudoku.Board;

public class BoardInitializer {

  private BoardAnalyzer analyzer;

  public BoardInitializer(BoardAnalyzer analyzer) {
    this.analyzer = analyzer;
  }

  public void initializeBoardWithPossibleValues(Board board) {
    for (var row = 0; row < 9; row++) {
      for (var col = 0; col < 9; col++) {
        var subgroup = board.getSubgroupAt(row, col);
        var subgroupRow = row - subgroup.getStartRow();
        var subgroupCol = col - subgroup.getStartCol();
        var value = subgroup.getValueAt(subgroupRow, subgroupCol);
        if (value == null) {
          for (var v = 1; v <= 9; v++) {
            if (!subgroup.contains(v) && !analyzer.isValueInRow(board, row, v) && !analyzer.isValueInCol(board, col, v)) {
              subgroup.addPossibleValueAt(subgroupRow, subgroupCol, v);
            }
          }
        }
      }
    }
  }

}