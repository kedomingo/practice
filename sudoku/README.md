# Sudoku solver

This uses the simple algorithm of iterative uniqueness checking.

The initial possible values are generated from the given input.
The possible values for each cell are checked against:

1. Its subgroup (the 3x3 group it belongs to)
2. Its row
3. Its column

If a possible value is unique in any of the above categories, then that is the solution for that cell.


This solution does not support "guessing" and backtracking normally done in hard-level sudoku



Files: 

1. index.php - used to generaate index.html. Run as `php index.php > index.html`
2. sudoku.js - The solution in JS

Open index.html in your browser to run it
