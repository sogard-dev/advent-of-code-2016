package day12

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day23Test {

    @Test
    fun testTaskOne() {
        assertEquals(
            3, task1(
                """cpy 2 a
tgl a
tgl a
tgl a
cpy 1 a
dec a
dec a""".lines()
            )
        )
        assertEquals(11736, task1(readPuzzle(), 7))
    }

    @Test
    fun testTaskTwo() {
        assertEquals(479008296, task1(readPuzzle(), 12))
    }

    private fun readPuzzle(): List<String> {
        val path = Paths.get(this.javaClass.getResource("/day23_puzzle_input.txt").toURI())
        val readAllLines = Files.readAllLines(path)
        return readAllLines
    }
}