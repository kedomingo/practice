package sudoku.service;

import sudoku.Board;

public class BoardReader {

  public Board readBoardAsString(String data) {
    var board = new Board();

    var rowNum = 0;
    var rows = data.split("\n");

    for (var row : rows) {
      var cols = row.split("\\|");
      var colNum = 0;
      for (var cellValue : cols) {
        if (!cellValue.trim().equals("")) {
          board.setValueAt(rowNum, colNum, Integer.parseInt(cellValue));
        }
        colNum++;
      }
      rowNum++;
    }

    return board;
  }
}