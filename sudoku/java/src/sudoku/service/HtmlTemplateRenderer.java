package sudoku.service;

public class HtmlTemplateRenderer {

  public String renderTemplate(String contents) {
    return  ""
      + "<html>"
      + "<head>"
      + "    <title>Sudoku solver</title>"
      + "    <style>"
      + "        body {"
      + "      font-family: sans-serif;"
      + "    }"
      + "    </style>"
      + "</head>"
      + "<body>"
      + contents
      + "</body>"
      + "</html>";
  }
}