package sudoku;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import sudoku.service.BoardReader;
import sudoku.service.HtmlBoardRenderer;
import sudoku.service.HtmlTemplateRenderer;
import sudoku.service.SampleBoardProvider;

public class Sudoku {

  public static void main(String[] args) throws IOException {
    (new Sudoku()).run();
  }

  private void run() throws IOException {
    var sampleProvider = new SampleBoardProvider();
    var boardReader = new BoardReader();
    var renderer = new HtmlBoardRenderer();
    var templateRenderer = new HtmlTemplateRenderer();

    var board = boardReader.readBoardAsString(sampleProvider.easySample());
    board.initializeBoardWithPossibleValues();

    var renderedBoard = templateRenderer.renderTemplate(
        renderer.renderBoard(board));

    BufferedWriter writer = new BufferedWriter(new FileWriter("out.html"));
    writer.write(renderedBoard);
    writer.close();
  }
}