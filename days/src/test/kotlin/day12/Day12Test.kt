package day12

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day12Test {

    @Test
    fun testTaskOne() {
        assertEquals(
            42, task1(
                listOf(
                    "cpy 41 a",
                    "inc a",
                    "inc a",
                    "dec a",
                    "jnz a 2",
                    "dec a"
                )
            )
        )

        assertEquals(318077, task1(readPuzzle()))
    }

    @Test
    fun testTaskTwo() {
        assertEquals(9227731, task2(readPuzzle()))
    }

    private fun readPuzzle(): List<String> {
        val path = Paths.get(this.javaClass.getResource("/day12_puzzle_input.txt").toURI())
        val readAllLines = Files.readAllLines(path)
        return readAllLines
    }
}