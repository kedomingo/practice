package sudoku.factory;

import sudoku.Board;
import sudoku.service.BoardReader;

public class BoardFactory {

  private BoardReader boardReader;

  public BoardFactory(BoardReader boardReader) {
    this.boardReader = boardReader;
  }

  public Board createFromString(String boardAsString) {
    return boardReader.readBoardAsString(boardAsString);
  }
}