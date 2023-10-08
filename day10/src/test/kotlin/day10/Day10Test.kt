package day10

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day10Test {

    @Test
    fun testTaskOne() {
        assertEquals(
            2, task1(
                listOf(
                    "value 5 goes to bot 2",
                    "bot 2 gives low to bot 1 and high to bot 0",
                    "value 3 goes to bot 1",
                    "bot 1 gives low to output 1 and high to bot 0",
                    "bot 0 gives low to output 2 and high to output 0",
                    "value 2 goes to bot 2"
                ), 2, 5
            )
        )

        assertEquals(141, task1(readPuzzle(), 17, 61))
    }

    @Test
    fun testTaskTwo() {
        assertEquals(1209, task2(readPuzzle()))
    }

    private fun readPuzzle(): List<String> {
        val path = Paths.get(this.javaClass.getResource("/puzzle_input.txt").toURI())
        val readAllLines = Files.readAllLines(path)
        return readAllLines
    }
}