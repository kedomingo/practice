<html>
<head>
    <title>Sudoku solver</title>
    <style>
        body {
            font-family: sans-serif;
        }
    </style>
</head>
<body>
    <table border="1" style="border-collapse: collapse" id="board">
        <?php for($i = 0; $i < 9; $i++): ?>
            <tr>
                <?php for($j = 0; $j < 9; $j++): ?>
                    <td <?php if ((in_array($i, [0, 1, 2, 6, 7, 8]) && in_array($j, [0, 1, 2, 6, 7, 8])) || (in_array($i, [3, 4, 5]) && in_array($j, [3, 4, 5]))) { echo 'style="background: #ccc"'; } ?>>
                        <input type="text" size="2" style="background: none;" id="input-<?php echo $i.'-'.$j; ?>" maxlength="1">
                    </td>
                <?php endfor; ?>
            </tr>
        <?php endfor; ?>
    </table>
    <br />
    <input type="button" value="Sample data" onclick="prefillWithSampleData()">
    <input type="button" value="Solve!" onclick="solve()">


    <script src="sudoku.js" type="text/javascript"></script>
</body>
</html>
