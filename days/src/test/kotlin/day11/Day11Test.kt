package day11

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day11Test {

    @Test
    fun testTaskOne() {
        assertEquals(
            11, task1(
                listOf(
                    "The first floor contains a hydrogen-compatible microchip and a lithium-compatible microchip.",
                    "The second floor contains a hydrogen generator.",
                    "The third floor contains a lithium generator.",
                    "The fourth floor contains nothing relevant."
                )
            )
        )

        assertEquals(-1, task1(readPuzzle()))
    }

    @Test
    fun testTaskTwo() {
        assertEquals(1209, task2(readPuzzle()))
    }

    private fun readPuzzle(): List<String> {
        val path = Paths.get(this.javaClass.getResource("/day11_puzzle_input.txt").toURI())
        val readAllLines = Files.readAllLines(path)
        return readAllLines
    }
}