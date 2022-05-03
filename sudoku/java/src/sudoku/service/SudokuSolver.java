package sudoku.service;

import sudoku.Board;

public class SudokuSolver {

  private final int RUN_LIMIT = 10;

  private BoardResolver resolver;
  private HtmlBoardRenderer outputRenderer;
  private String outputHtml = "";

  public SudokuSolver(BoardResolver resolver, HtmlBoardRenderer outputRenderer) {
    this.resolver = resolver;
    this.outputRenderer = outputRenderer;
  }

  /**
   * Performs one pass of the solving algorithm.
   * 1. Resolve cells with only one possible value
   * 2. Remove possible values that are unique in:
   *    a. Its subgroup
   *    b. Its column
   *    c. Its row
   * 3. Determine if a possible value only exists in one row in its subgroup and remove this value from
   *    the possible values in the same row of the horizontally adjacent
   *    subgroups
   * 4. Determine if a possible value only exists in one column in its subgroup and remove this value from the
   *    possible values in the same column of the vertically adjacent subgroups
   * 5. If a possible value is only repeated in one column or row in the subgroup, then the other vertical
   *    or horizontal subgroups (respectively) cannot use that possible value in the same column or row
   */
  public void solve(Board board) {
    solve(board, 1);
  }

  public void solve(Board board, int iterationCount) {
    if (iterationCount > RUN_LIMIT) {
      return;
    }

    var last = board.toString();

    outputHtml += String.format(
        "<h3>Iteration %d - Resolving Single Cells</h3>%s<hr />", iterationCount, outputRenderer.renderBoard(board));
    resolver.resolveSinglePossibleValues(board);

    outputHtml += String.format(
        "<h3>Iteration %d - Resolving Cells Uniqueness</h3>%s<hr />", iterationCount,
        outputRenderer.renderBoard(board)
    );
    resolver.resolveCellUniqueness(board);

    outputHtml += String.format(
        "<h3>Iteration %d - Resolving Column Restrictions</h3>%s<hr />", iterationCount,
        outputRenderer.renderBoard(board)
    );
    resolver.resolveColumnRestriction(board);
    resolver.resolveRowRestriction(board);

    var current = board.toString();

    // No change in the board
    if (last.equals(current)) {
      return;
    }

    solve(board, iterationCount + 1);
  }

  public String getOutputHtml() {
    return outputHtml;
  }
}