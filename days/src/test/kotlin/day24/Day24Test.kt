package day24

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day24Test {

    @Test
    fun testTaskOne() {
        assertEquals(
            14, task1(
                """###########
#0.1.....2#
#.#######.#
#4.......3#
###########""".lines()
            )
        )
        assertEquals(498, task1(readPuzzle()))
    }

    @Test
    fun testTaskTwo() {
        assertEquals(804, task2(readPuzzle()))
    }

    private fun readPuzzle(): List<String> {
        val path = Paths.get(this.javaClass.getResource("/day24_puzzle_input.txt").toURI())
        val readAllLines = Files.readAllLines(path)
        return readAllLines
    }
}