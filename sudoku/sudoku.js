function solve() {
    var inputMatrix = readMatrix();
    var penciledIn = pencilIn(inputMatrix);
    var solution = "<hr /><h3>Initial Possible Values</h3>" + renderMatrix(penciledIn);
    var i = 1;
    while ((penciledIn = findSolution(penciledIn)) !== false) {
        console.log("-------- Iteration " + i);
        solution += "<hr /><h3>Iteration " + i++ + "</h3>" + renderMatrix(penciledIn);
        if (i == 100) {
            break;
        }
    }
    document.body.innerHTML += solution;
    writeMatrix(inputMatrix);
}

function findSolution(matrix) {
    // Clear the board of cells with only one possible value
    // If there is only one possible value, that is the solution
    var isMatrixChanged = false;
    for (var i = 0; i < 9; i++) {
        for (var j = 0; j < 9; j++) {
            if (matrix[i][j]['value'].trim() !== "") {
                continue;
            }
            for (v in matrix[i][j]['possibleValues']) {
                if (Object.keys(matrix[i][j]['possibleValues']).length === 1) {
                    console.log("Found " + v + " at " + i + ", " + j);
                    matrix = setSolution(matrix, i, j, v);
                    isMatrixChanged = true;
                }
            }
        }
    }
    // If a possible value is unique in a group, a row, or a column, then that is the solution.
    // Return early and recalculate using the new information
    for (var i = 0; i < 9; i++) {
        for (var j = 0; j < 9; j++) {
            if (matrix[i][j]['value'].trim() !== "") {
                continue;
            }
            for (v in matrix[i][j]['possibleValues']) {
                if (!isInGroup(matrix, i, j, v, true)
                    || !isInRow(matrix, i, j, v, true)
                    || !isInCol(matrix, i, j, v, true)) {

                    console.log("Found " + v + " at " + i + ", " + j);
                    return setSolution(matrix, i, j, v);
                }
            }
        }
    }
    // If there are cells with only 1 possible value and they were cleared, treat them as one iteration group
    if (isMatrixChanged) {
        return matrix;
    }

    // If a possible value is only repeated in one column or row in the subgroup,
    // then the other vertical or horizontal subgroups (respectively) cannot use that possible value in the same column
    // or row

    for (var i = 0; i < 9; i++) {
        for (var j = 0; j < 9; j++) {
            if (matrix[i][j]['value'].trim() !== "") {
                continue;
            }

            // Check columns
            for (v in matrix[i][j]['possibleValues']) {
                var result = false;

                var possibleColumns = getColsOfPossibleValue(matrix, i, j, v);
                if (Object.keys(possibleColumns).length === 1) {
                    console.log(">>>>>> " + v + " in " + i + ',' + j + " is only in one column");
                    result = clearColsOfOtherSubgroupOfPossibleValue(matrix, i, j, v);
                }
                if (result !== false) {
                    isMatrixChanged = true;
                    matrix = result;
                }
            }

            // Check rows
            for (v in matrix[i][j]['possibleValues']) {
                var possibleRows = getRowsOfPossibleValue(matrix, i, j, v);
                if (Object.keys(possibleRows).length === 1) {
                    console.log("+++++++++ " + v + " in " + i + ',' + j + " is only in one row");
                    result = clearRowsOfOtherSubgroupOfPossibleValue(matrix, i, j, v);
                }
                if (result !== false) {
                    isMatrixChanged = true;
                    matrix = result;
                }
            }
        }
    }
    if (isMatrixChanged) {
        console.log("Matrix is changed");
        return matrix;
    }

    console.log("Matrix is not changed");
    return false;
}

/**
 * Set the solution of a cell, clearing its group, row, and col of possible values
 */
function setSolution(matrix, row, col, value) {
    matrix[row][col].value = '' + value; // coerce as string
    matrix[row][col]['possibleValues'] = {};
    matrix = removePossibleValueForGroup(matrix, row, col, value);
    matrix = removePossibleValueForRow(matrix, row, col, value);
    matrix = removePossibleValueForColumn(matrix, row, col, value);

    return matrix;
}

/**
 * Populate the initial possible values for the empty cells
 */
function pencilIn(matrix) {
    for (var i = 0; i < 9; i++) {
        for (var j = 0; j < 9; j++) {
            if (matrix[i][j]['value'].trim() !== "") {
                continue;
            }
            if (typeof matrix[i][j]['possibleValues'] === 'undefined') {
                matrix[i][j]['possibleValues'] = {};
            }
            for (var v = 1; v <= 9; v++) {
                if (!isInGroup(matrix, i, j, v) && !isInRow(matrix, i, j, v) && !isInCol(matrix, i, j, v)) {
                    matrix[i][j]['possibleValues'][v] = v;
                }
            }
        }
    }

    return matrix;
}

/**
 * Check if the value is in the subgroup. If `isIncludePossibleValues` is set to true, it will also check the possible
 * values of the empty cells
 */
function isInGroup(matrix, row, col, value, isIncludePossibleValues) {
    if (typeof isIncludePossibleValues === 'undefined') {
        isIncludePossibleValues = false;
    } else {
        isIncludePossibleValues = true;
    }
    var startRow = 3 * Math.floor(row / 3);
    var startCol = 3 * Math.floor(col / 3);
    for (var i = startRow; i < startRow + 3; i++) {
        for (var j = startCol; j < startCol + 3; j++) {
            if (i === row && j === col) {
                continue;
            }
            if (matrix[i][j]['value'] == value || (isIncludePossibleValues && isPossibleValue(matrix[i][j], value))) {
                return true;
            }
        }
    }
    return false;
}

function isInRow(matrix, row, col, value, isIncludePossibleValues) {
    if (typeof isIncludePossibleValues === 'undefined') {
        isIncludePossibleValues = false;
    } else {
        isIncludePossibleValues = true;
    }
    for (var j = 0; j < 9; j++) {
        if (j === col) {
            continue;
        }
        if (matrix[row][j]['value'] == value || (isIncludePossibleValues && isPossibleValue(matrix[row][j], value))) {
            return true;
        }
    }
    return false;
}

function isInCol(matrix, row, col, value, isIncludePossibleValues) {
    if (typeof isIncludePossibleValues === 'undefined') {
        isIncludePossibleValues = false;
    } else {
        isIncludePossibleValues = true;
    }
    for (var i = 0; i < 9; i++) {
        if (i === row) {
            continue;
        }
        if (matrix[i][col]['value'] == value || (isIncludePossibleValues && isPossibleValue(matrix[i][col], value))) {
            return true;
        }
    }
    return false;
}

function removePossibleValueForGroup(matrix, row, col, value) {
    var startRow = 3 * Math.floor(row / 3);
    var startCol = 3 * Math.floor(col / 3);
    for (var i = startRow; i < startRow + 3; i++) {
        for (var j = startCol; j < startCol + 3; j++) {
            if (typeof matrix[i][j]['possibleValues'] !== 'undefined') {
                delete matrix[i][j]['possibleValues'][value];
            }
        }
    }

    return matrix;
}

function removePossibleValueForRow(matrix, row, col, value) {
    for (var j = 0; j < 9; j++) {
        if (typeof matrix[row][j]['possibleValues'] !== 'undefined') {
            delete matrix[row][j]['possibleValues'][value];
        }
    }

    return matrix;
}

function removePossibleValueForColumn(matrix, row, col, value) {
    for (var i = 0; i < 9; i++) {
        if (typeof matrix[i][col]['possibleValues'] !== 'undefined') {
            delete matrix[i][col]['possibleValues'][value];
        }
    }

    return matrix;
}

function isPossibleValue(matrixCell, value) {
    if (typeof matrixCell['possibleValues'] === 'undefined') {
        return false;
    }

    return typeof matrixCell['possibleValues'][value] !== 'undefined';
}

function getColsOfPossibleValue(matrix, row, col, value) {
    const startRow = 3 * Math.floor(row / 3);
    const startCol = 3 * Math.floor(col / 3);

    const cols = {};
    for (var i = startRow; i < startRow + 3; i++) {
        for (var j = startCol; j < startCol + 3; j++) {
            if (isPossibleValue(matrix[i][j], value)) {
                cols[j] = j;
            }
        }
    }
    return cols;
}

function getRowsOfPossibleValue(matrix, row, col, value) {
    const startRow = 3 * Math.floor(row / 3);
    const startCol = 3 * Math.floor(col / 3);

    const rows = {};
    for (var i = startRow; i < startRow + 3; i++) {
        for (var j = startCol; j < startCol + 3; j++) {
            if (isPossibleValue(matrix[i][j], value)) {
                rows[i] = i;
            }
        }
    }
    return rows;
}

function clearColsOfOtherSubgroupOfPossibleValue(matrix, row, col, value) {
    const startRow = 3 * Math.floor(row / 3);
    const startCol = 3 * Math.floor(col / 3);
    const rowStarts = [0, 3, 6];
    var hasChanges = false;
    rowStarts.forEach((subgroupStartRow) => {
        // Skip subgroup row,col is in
        if (subgroupStartRow === startRow) {
            return;
        }
        console.log("Clearing " + value + " from col " + col + " of group starting in row " + subgroupStartRow);
        for (var i = subgroupStartRow; i < subgroupStartRow + 3; i++) {
            if (isPossibleValue(matrix[i][col], value)) {
                console.log('Removing ' + value + ' from column ' + i + ',' + col);
                hasChanges = true;
                delete matrix[i][col]['possibleValues'][value];
            }
        }
    });

    return hasChanges ? matrix : false
}

function clearRowsOfOtherSubgroupOfPossibleValue(matrix, row, col, value) {
    const startRow = 3 * Math.floor(row / 3);
    const startCol = 3 * Math.floor(col / 3);
    const colStarts = [0, 3, 6];
    var hasChanges = false;
    colStarts.forEach((subgroupStartCol) => {
        // Skip subgroup row,col is in
        if (subgroupStartCol === startCol) {
            return;
        }
        console.log("Clearing " + value + " from row " + row + " of group starting in col " + subgroupStartCol);
        for (var j = subgroupStartCol; j < subgroupStartCol + 3; j++) {
            if (isPossibleValue(matrix[row][j], value)) {
                console.log('Removing ' + value + ' from row ' + row + ',' + j);
                hasChanges = true;
                delete matrix[row][j]['possibleValues'][value];
            }
        }
    });

    return hasChanges ? matrix : false;
}

function prefillWithSampleData(isHard) {
    var data = ''
        + '4|1| |7| |5|9| | ' + "\n"
        + ' | | | | | | | | ' + "\n"
        + ' |7| |1|8| | | |4' + "\n"
        + ' | |4| |7|6| | | ' + "\n"
        + ' |6|3|9|4| | | | ' + "\n"
        + '5| | | |3|1|4|2|6' + "\n"
        + ' |8| |4|5|9|6|7| ' + "\n"
        + ' | | | |1|7| |8| ' + "\n"
        + ' | |9| |6|8| |4|3'
    ;
    if (typeof isHard !== 'undefined' && !!isHard) {
        data = ''
            + ' | |2| |9| |3| | ' + "\n"
            + '8| |1| | | | | | ' + "\n"
            + '5| | | | | | | | ' + "\n"
            + ' |9| | |6| | |4| ' + "\n"
            + ' | | | | | | |1|8' + "\n"
            + ' | | | | | | | |5' + "\n"
            + ' |7| | | | |2| | ' + "\n"
            + '3| | |5| | | | | ' + "\n"
            + ' | | |1| | | | | '
        ;
    }

    var rowNum = 0;
    var rows = data.split("\n");
    rows.forEach((row) => {
        var cols = row.split("|");
        var colNum = 0;
        cols.forEach((cell) => {
            document.getElementById('input-' + rowNum + '-' + colNum).value = cell;
            colNum++;
        });
        rowNum++;
    });
}

function readMatrix() {
    var m = {};
    for (var row = 0; row < 9; row++) {
        if (typeof m[row] === 'undefined') {
            m[row] = {};
        }
        for (var col = 0; col < 9; col++) {
            m[row][col] = {'value': document.getElementById('input-' + row + '-' + col).value};
        }
    }
    return m;
}

function writeMatrix(matrix) {
    for (var row = 0; row < 9; row++) {
        if (typeof matrix[row] === 'undefined') {
            continue;
        }
        for (var col = 0; col < 9; col++) {
            document.getElementById('input-' + row + '-' + col).value = matrix[row][col].value || '';
        }
    }
}

function renderMatrix(matrix) {
    var s = '<table style="border-collapse: collapse" border="1">';

    for (var row = 0; row < 9; row++) {
        s += '<tr>';
        for (var col = 0; col < 9; col++) {
            var background = '';
            if (([0, 1, 2, 6, 7, 8].indexOf(row) >= 0 && [0, 1, 2, 6, 7, 8].indexOf(col) >= 0) || ([3, 4, 5].indexOf(row) >= 0 && [3, 4, 5].indexOf(col) >= 0)) {
                background = 'background: #ddd';
            }

            s += '<td ';
            if (matrix[row][col].value.trim() !== '') {
                s += ' style="padding:10px; ' + background + '">' + matrix[row][col].value;
            } else {
                s += ' style="' + background + '"> ' + renderPossibleValues(matrix[row][col].possibleValues);
            }
            s += '</td>';
        }
        s += '</tr>';
    }
    s += '</table>';

    return s;
}

function renderPossibleValues(values) {
    var s = '<table style="font-size: 8px">';
    s += '<tr>';
    for (var v = 1; v <= 9; v++) {
        if (v === 4 || v === 7) {
            s += '</tr><tr>';
        }
        s += '<td>';
        if (typeof values[v] !== 'undefined') {
            s += v;
        }
        s += '</td>';
    }
    s += '</tr>';
    s += '</table>';

    return s;
}


// prefillWithSampleData();
