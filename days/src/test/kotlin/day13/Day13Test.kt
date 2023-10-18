package day13

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day13Test {

    @Test
    fun testTaskOne() {
        assertEquals(11, task1(listOf("10"), 7, 4))
        assertEquals(86, task1(listOf("1364"), 31, 39))
    }

    @Test
    fun testTaskTwo() {
        assertEquals(12, task2(listOf("10"), 6))
        assertEquals(127, task2(listOf("1364"), 50))
    }

    private fun readPuzzle(): List<String> {
        val path = Paths.get(this.javaClass.getResource("/day13_puzzle_input.txt").toURI())
        val readAllLines = Files.readAllLines(path)
        return readAllLines
    }
}