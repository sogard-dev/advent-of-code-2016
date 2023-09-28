package day8

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day8Test {

    @Test
    fun manual() {
        val display = Day8.Display(7, 3)
        display.rect(3, 2)
        assertEquals("###....###...........", display.toLine())

        display.rotateColumn(1, 1)
        assertEquals("#.#....###.....#.....", display.toLine())

        display.rotateRow(0, 4)
        assertEquals("....#.####.....#.....", display.toLine())

        display.rotateColumn(1, 1)
        assertEquals(".#..#.##.#.....#.....", display.toLine())

        assertEquals(6, display.count())
    }

    @Test
    fun testTaskOne() {
        assertEquals(123, Day8.task1(readPuzzle()))
    }

    @Test
    fun testTaskTwo() {
        assertEquals(2, Day8.task2(readPuzzle()))
    }

    private fun readPuzzle(): List<String> {
        val path = Paths.get(this.javaClass.getResource("/puzzle_input.txt").toURI())
        val readAllLines = Files.readAllLines(path)
        return readAllLines
    }
}