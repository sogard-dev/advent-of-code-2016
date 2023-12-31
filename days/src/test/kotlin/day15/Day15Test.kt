package day15

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day15Test {

    @Test
    fun testTaskOne() {
        assertEquals(
            5, task1(
                listOf(
                    "Disc #1 has 5 positions; at time=0, it is at position 4.",
                    "Disc #2 has 2 positions; at time=0, it is at position 1."
                )
            )
        )

        assertEquals(400589, task1(readPuzzle()))
    }

    @Test
    fun testTaskTwo() {
        assertEquals(3045959, task2(readPuzzle()))
    }

    private fun readPuzzle(): List<String> {
        val path = Paths.get(this.javaClass.getResource("/day15_puzzle_input.txt").toURI())
        val readAllLines = Files.readAllLines(path)
        return readAllLines
    }
}