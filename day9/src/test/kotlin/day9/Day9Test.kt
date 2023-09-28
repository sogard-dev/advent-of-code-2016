package day9

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day9Test {

    @Test
    fun testTaskOne() {
        assertEquals(6, Day9.task1(listOf("ADVENT")))
        assertEquals(7, Day9.task1(listOf("A(1x5)BC")))
        assertEquals(9, Day9.task1(listOf("(3x3)XYZ")))
        assertEquals(6, Day9.task1(listOf("(6x1)(1x3)A")))
        assertEquals(11, Day9.task1(listOf("A(2x2)BCD(2x2)EFG")))
        assertEquals(18, Day9.task1(listOf("X(8x2)(3x3)ABCY")))
        assertEquals(98135, Day9.task1(readPuzzle()))
    }

    @Test
    fun testTaskTwo() {
        assertEquals(9, Day9.task2(listOf("(3x3)XYZ")))
        assertEquals(6, Day9.task2(listOf("ADVENT")))
        assertEquals("XABCABCABCABCABCABCY".length, Day9.task2(listOf("X(8x2)(3x3)ABCY")))
        assertEquals(241920, Day9.task2(listOf("(27x12)(20x12)(13x14)(7x10)(1x12)A")))
        assertEquals(445, Day9.task2(listOf("(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN")))
    }

    private fun readPuzzle(): List<String> {
        val path = Paths.get(this.javaClass.getResource("/puzzle_input.txt").toURI())
        val readAllLines = Files.readAllLines(path)
        return readAllLines
    }
}