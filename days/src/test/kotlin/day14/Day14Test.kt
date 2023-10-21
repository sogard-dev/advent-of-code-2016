package day14

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day14Test {

    @Test
    fun testTaskOne() {
        assertEquals(22728, task1(listOf("abc")))
        assertEquals(16106, task1(listOf("zpqevtbw")))
    }

    @Test
    fun testTaskTwo() {
        assertEquals(22551, task2(listOf("abc")))
        assertEquals(22423, task2(listOf("zpqevtbw")))
    }

    private fun readPuzzle(): List<String> {
        val path = Paths.get(this.javaClass.getResource("/day14_puzzle_input.txt").toURI())
        val readAllLines = Files.readAllLines(path)
        return readAllLines
    }
}