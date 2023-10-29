package day20

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day20Test {

    @Test
    fun testTaskOne() {
        assertEquals(3, task1(listOf("5-8", "0-2", "4-7"), 0, 9))
        assertEquals(32259706, task1(readPuzzle(), 0, 4294967295))
    }

    @Test
    fun testTaskTwo() {
        assertEquals(2, task2(listOf("5-8", "0-2", "4-7"), 0, 9))
        assertEquals(113, task2(readPuzzle(), 0, 4294967295))
    }

    private fun readPuzzle(): List<String> {
        val path = Paths.get(this.javaClass.getResource("/day20_puzzle_input.txt").toURI())
        val readAllLines = Files.readAllLines(path)
        return readAllLines
    }
}