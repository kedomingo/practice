package sudoku;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import sudoku.factory.BoardFactory;
import sudoku.service.BoardAnalyzer;
import sudoku.service.BoardInitializer;
import sudoku.service.BoardReader;
import sudoku.service.BoardResolver;
import sudoku.service.HtmlBoardRenderer;
import sudoku.service.HtmlTemplateRenderer;
import sudoku.service.SampleBoardProvider;
import sudoku.service.SudokuSolver;

public class Sudoku {

  public static void main(String[] args) throws IOException {
    (new Sudoku()).run();
  }

  private void run() throws IOException {
    var renderer = new HtmlBoardRenderer();
    var templateRenderer = new HtmlTemplateRenderer();
    var boardAnalyzer = new BoardAnalyzer();
    var resolver = new BoardResolver(boardAnalyzer);
    var board = (new BoardFactory(new BoardReader())).createFromString((new SampleBoardProvider()).hardSample());
    var boardInitializer = new BoardInitializer(boardAnalyzer);
    var solver = new SudokuSolver(resolver, renderer);

    boardInitializer.initializeBoardWithPossibleValues(board);
    solver.solve(board);

    var contents = "<h3>Initial Values</h3>" + renderer.renderBoard(board) + "<hr />";
    contents += solver.getOutputHtml();

    var renderedBoard = templateRenderer.renderTemplate(contents);

    BufferedWriter writer = new BufferedWriter(new FileWriter("out.html"));
    writer.write(renderedBoard);
    writer.close();
  }
}