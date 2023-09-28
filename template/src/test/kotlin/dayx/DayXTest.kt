package dayx

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals

internal class DayXTest {

    @Test
    fun testTaskOne() {
        assertEquals(110, DayX.task1(readPuzzle()))
    }

    @Test
    fun testTaskTwo() {
        assertEquals(242, DayX.task2(readPuzzle()))
    }

    private fun readPuzzle(): List<String> {
        val path = Paths.get(this.javaClass.getResource("/puzzle_input.txt").toURI())
        val readAllLines = Files.readAllLines(path)
        return readAllLines
    }
}