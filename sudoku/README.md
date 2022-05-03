# Sudoku solver

This uses the simple algorithm of iterative uniqueness checking.

The initial possible values are generated from the given input.
The possible values for each cell are checked against:

1. Its subgroup (the 3x3 group it belongs to)
2. Its row
3. Its column

If a possible value is unique in any of the above categories, then that is the solution for that cell.


NOTE: This solution does not support "guessing" and backtracking normally done in hard-level sudoku. There are cases where the puzzle is unsolvable by this algorithm

The page will show each step of the solution. The browser console log shows the order of the numbers which are found, and their coordinate in the board.



Files: 

1. index.php - used to generaate index.html. Run as `php index.php > index.html` (optional)
2. sudoku.js - The solution in JS

Open index.html in your browser to run it


Click on sample data to load a test Sudoku problem

<img width="295" alt="Screen Shot 2022-05-01 at 4 54 45 PM" src="https://user-images.githubusercontent.com/1763107/166151478-7e37949b-4e9b-4ea7-bf6e-1608113ea6f3.png">


Click on the solve button to run the program

<img width="360" alt="Screen Shot 2022-05-01 at 4 57 54 PM" src="https://user-images.githubusercontent.com/1763107/166151602-592dbab3-5e01-4b99-b5c4-66cbd7a85eb8.png">

...

<img width="302" alt="Screen Shot 2022-05-01 at 4 57 14 PM" src="https://user-images.githubusercontent.com/1763107/166151578-92fcc868-5600-45d1-abdc-aee5cdcf269a.png">


## Java

The java version is a rewrite of the same JS logic with the addition of brute force search in case of hard problems where a "guess" is necessary. The search is only one-level i.e., not recursve -- so only one guess at a time can be made. For very hard problems where multiple guesses are necessary, the recursive solution is not yet implemented.

Solution for an "Extreme" sudoku requiring 1 guess:

![Screen Shot 2022-05-03 at 6 43 05 PM](https://user-images.githubusercontent.com/1763107/166500850-f09c524e-91ca-4491-9b2b-ac6675e4a7b7.png)

Solution including to encode the numbers, and encode the solution

![279424830_958079951539510_4351082658686304077_n](https://user-images.githubusercontent.com/1763107/166501090-4422b02f-c3ad-4b11-92b3-12cd2712f942.jpeg)

