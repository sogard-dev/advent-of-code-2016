package day18


fun task1(input: String, rows: Int): Int {
    var currentRow = input
    var safeTiles = countSafe(currentRow)
    (1 until rows).forEach { _ ->
        currentRow = generateRow(currentRow)
        safeTiles += countSafe(currentRow)

    }
    return safeTiles
}

fun generateRow(currentRow: String): String {
    return currentRow.withIndex().map { (i, c) ->
        val (left, center, right) = (i - 1..i + 1).map { upper -> currentRow.getOrElse(upper) { '.' } }
        when (Triple(left, center, right)) {
            Triple('^', '^', '.') -> '^'
            Triple('.', '^', '^') -> '^'
            Triple('.', '.', '^') -> '^'
            Triple('^', '.', '.') -> '^'
            else -> '.'
        }
    }.joinToString("")
}

private fun countSafe(currentRow: String) = currentRow.count { it == '.' }

fun task2(input: List<String>): Int {
    return -1
}