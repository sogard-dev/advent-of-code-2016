package day21

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day21Test {

    @Test
    fun testTaskOne() {
        assertEquals("412305", task1("012345", listOf("swap position 4 with position 0")))
        assertEquals("412305", task1("012345", listOf("swap position 0 with position 4")))
        assertEquals("014325", task1("012345", listOf("swap letter 2 with letter 4")))
        assertEquals("014325", task1("012345", listOf("swap letter 4 with letter 2")))
        assertEquals("123450", task1("012345", listOf("rotate left 1 step")))
        assertEquals("123450", task1("012345", listOf("rotate left 7 step")))
        assertEquals("501234", task1("012345", listOf("rotate right 1 step")))
        assertEquals("501234", task1("012345", listOf("rotate right 7 step")))
        assertEquals("450123", task1("012345", listOf("rotate based on position of letter 1")))
        assertEquals("234501", task1("012345", listOf("rotate based on position of letter 3")))
        assertEquals("014325", task1("012345", listOf("reverse positions 2 through 4")))
        assertEquals("210345", task1("012345", listOf("reverse positions 0 through 2")))
        assertEquals("023415", task1("012345", listOf("move position 1 to position 4")))
        assertEquals("012354", task1("012345", listOf("move position 4 to position 5")))

        assertEquals("ebcda", task1("abcde", listOf("swap position 4 with position 0")))
        assertEquals("edcba", task1("ebcda", listOf("swap letter d with letter b")))
        assertEquals("abcde", task1("edcba", listOf("reverse positions 0 through 4")))
        assertEquals("bcdea", task1("abcde", listOf("rotate left 1 step")))
        assertEquals("bdeac", task1("bcdea", listOf("move position 1 to position 4")))
        assertEquals("abdec", task1("bdeac", listOf("move position 3 to position 0")))
        assertEquals("ecabd", task1("abdec", listOf("rotate based on position of letter b")))
        assertEquals("decab", task1("ecabd", listOf("rotate based on position of letter d")))

        assertEquals("hcdefbag", task1("abcdefgh", readPuzzle()))
    }

    @Test
    fun testTaskTwo() {
        assertEquals("abcdefgh", task2("hcdefbag", readPuzzle()))

        assertEquals("fbhaegdc", task2("fbgdceah", readPuzzle()))
    }

    private fun readPuzzle(): List<String> {
        val path = Paths.get(this.javaClass.getResource("/day21_puzzle_input.txt").toURI())
        val readAllLines = Files.readAllLines(path)
        return readAllLines
    }
}