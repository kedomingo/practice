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
    (new BoardInitializer(boardAnalyzer)).initializeBoardWithPossibleValues(board);

    var contents = "";

    contents += "<h3>Initial Values</h3>" + renderer.renderBoard(board) + "<hr />";

    var last = "";
    var current = "x";
    var runs = 1;
    var limit = 100;
    while (runs <= limit) {
      resolver.resolveSinglePossibleValues(board);
      current = board.toString();
      if (current.equals(last)) {
        System.out.println("No change in board. Exiting");
        break;
      }
      last = current;

      contents += "<h3>Iteration " + runs + " - Resolving Single Cells</h3>" + renderer.renderBoard(board) + "<hr />";

      resolver.resolveCellUniqueness(board);
      contents +=
          "<h3>Iteration " + runs + " - Resolving Cells Uniqueness</h3>" + renderer.renderBoard(board) + "<hr />";
      runs++;
    }

    var renderedBoard = templateRenderer.renderTemplate(contents);

    BufferedWriter writer = new BufferedWriter(new FileWriter("out.html"));
    writer.write(renderedBoard);
    writer.close();
  }

  /**
   * Performs one pass of the solving algorithm. 1. Resolve cells with only one possible value 2. Remove possible values
   * that are unique in: a. Its subgroup b. Its column c. Its row 3. Determine if a possible value only exists in one
   * row in its subgroup and remove this value from the possible values in the same row of the horizontally adjacent
   * subgroups 4. Determine if a possible value only exists in one column in its subgroup and remove this value from the
   * possible values in the same column of the vertically adjacent subgroups
   */
  private void solve(Board board) {

  }
}