package day17

import day14.toHex
import java.security.MessageDigest
import kotlin.math.max

val md = MessageDigest.getInstance("MD5")!!

fun task1(input: List<String>): String {
    val passcode = input[0]

    val paths = mutableListOf(Triple("", 0, 0))
    val newPaths: MutableList<Triple<String, Int, Int>> = mutableListOf()

    while (paths.isNotEmpty()) {
        for ((pathSoFar, row, column) in paths) {
            if (row == 3 && column == 3) {
                return pathSoFar
            }

            val options = options(passcode, pathSoFar)
            for (movement in options) {
                val newRow = row + movement.row
                val newColumn = column + movement.column
                if (newRow in 0..3 && newColumn in 0..3) {
                    newPaths.add(Triple(pathSoFar + movement.letter, newRow, newColumn))
                }
            }
        }

        paths.clear()
        paths.addAll(newPaths)
        newPaths.clear()
    }

    return ""
}

fun task2(input: List<String>): Int {
    val passcode = input[0]

    val paths = mutableListOf(Triple("", 0, 0))
    val newPaths: MutableList<Triple<String, Int, Int>> = mutableListOf()

    var longestPath = 0

    for (round in 0..100000) {
        if (paths.isEmpty()) {
            return longestPath
        }
        for ((pathSoFar, row, column) in paths) {
            if (row == 3 && column == 3) {
                longestPath = max(longestPath, round)
            } else {
                val options = options(passcode, pathSoFar)
                for (movement in options) {
                    val newRow = row + movement.row
                    val newColumn = column + movement.column
                    if (newRow in 0..3 && newColumn in 0..3) {
                        newPaths.add(Triple(pathSoFar + movement.letter, newRow, newColumn))
                    }
                }
            }
        }

        paths.clear()
        paths.addAll(newPaths)
        newPaths.clear()
    }

    return longestPath
}

private fun options(passcode: String, path: String): List<Movement> {
    return md.digest((passcode + path).toByteArray()).toHex()
        .take(4)
        .withIndex()
        .mapNotNull {
            if (it.value in 'b'..'f') {
                when (it.index) {
                    0 -> Movement.UP
                    1 -> Movement.DOWN
                    2 -> Movement.LEFT
                    3 -> Movement.RIGHT
                    else -> null
                }
            } else {
                null
            }
        }
        .toList()
}

private enum class Movement(val row: Int, val column: Int, val letter: String) {
    UP(0, -1, "U"),
    DOWN(0, 1, "D"),
    LEFT(-1, 0, "L"),
    RIGHT(1, 0, "R"),
}