package sudoku.service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import sudoku.Board;

public class HtmlBoardRenderer {

  public String renderBoard(Board board) {

    List<Integer> corners = Arrays.asList(0, 1, 2, 6, 7, 8);
    List<Integer> center = Arrays.asList(3, 4, 5);

    var s = "<table style=\"border-collapse: collapse\" border=\"1\">";
    for (var row = 0; row < 9; row++) {
      s += "<tr>";
      for (var col = 0; col < 9; col++) {

        var value = board.getValueAt(row, col);
        var possibleValues = board.getPossibleValuesAt(row, col);

        var style = "";
        if ((corners.contains(row) && corners.contains(col)) || (center.contains(row) && center.contains(col))) {
          style = "background: #ddd";
        }

        s += "<td ";
        if (value != null) {
          s += " style=\"padding: 10px;" + style + " \">" + value;
        } else {
          s += " style=\" " + style + " \"> " + renderPossibleValues(possibleValues);
        }
        s += "</td>";
      }
      s += "</tr>";
    }
    s += "</table>";

    return s;
  }

  private String renderPossibleValues(Set<Integer> values) {
    var s = "<table style=\"font-size: 8px\">";
    s += "<tr>";
    for (var v = 1; v <= 9; v++) {
      if (v == 4 || v == 7) {
        s += "</tr><tr>";
      }
      s += "<td>";
      if (values.contains(v)) {
        s += v;
      }
      s += "</td>";
    }
    s += "</tr>";
    s += "</table>";

    return s;
  }
}