package day18

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day18Test {

    @Test
    fun testTaskOne() {
        assertEquals("^^^...^..^", generateRow(".^^.^.^^^^"))
        assertEquals("^.^^.^.^^.", generateRow("^^^...^..^"))
        assertEquals("..^^...^^^", generateRow("^.^^.^.^^."))
        assertEquals(".^^^^.^^.^", generateRow("..^^...^^^"))
        assertEquals("^^..^.^^..", generateRow(".^^^^.^^.^"))
        assertEquals("^^^^..^^^.", generateRow("^^..^.^^.."))
        assertEquals("^..^^^^.^^", generateRow("^^^^..^^^."))
        assertEquals(".^^^..^.^^", generateRow("^..^^^^.^^"))
        assertEquals("^^.^^^..^^", generateRow(".^^^..^.^^"))

        assertEquals(38, task1(".^^.^.^^^^", 10))

        assertEquals(1974, task1(readPuzzle()[0], 40))
    }

    @Test
    fun testTaskTwo() {
        assertEquals(19991126, task1(readPuzzle()[0], 400000))
    }

    private fun readPuzzle(): List<String> {
        val path = Paths.get(this.javaClass.getResource("/day18_puzzle_input.txt").toURI())
        val readAllLines = Files.readAllLines(path)
        return readAllLines
    }
}