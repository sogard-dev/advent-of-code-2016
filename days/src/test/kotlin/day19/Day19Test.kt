package day19

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day19Test {

    @Test
    fun testTaskOne() {
        assertEquals(3, task1(listOf("5")))
        assertEquals(1808357, task1(listOf("3001330")))
    }

    @Test
    fun testTaskTwo() {
        assertEquals(2, task2(listOf("5")))
        assertEquals(1407007, task2(listOf("3001330")))
    }

    private fun readPuzzle(): List<String> {
        val path = Paths.get(this.javaClass.getResource("/day19_puzzle_input.txt").toURI())
        val readAllLines = Files.readAllLines(path)
        return readAllLines
    }
}