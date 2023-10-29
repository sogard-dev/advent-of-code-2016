package day21

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day22Test {

    @Test
    fun testTaskOne() {
        assertEquals(-1, task1(readPuzzle()))
    }

    @Test
    fun testTaskTwo() {
        assertEquals(-1, task2(readPuzzle()))
    }

    private fun readPuzzle(): List<String> {
        val path = Paths.get(this.javaClass.getResource("/day21_puzzle_input.txt").toURI())
        val readAllLines = Files.readAllLines(path)
        return readAllLines
    }
}