package day17

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day17Test {

    @Test
    fun testTaskOne() {
        assertEquals("DDRRRD", task1(listOf("ihgpwlah")))
        assertEquals("DDUDRLRRUDRD", task1(listOf("kglvqrro")))
        assertEquals("DRURDRUDDLLDLUURRDULRLDUUDDDRR", task1(listOf("ulqzkmiv")))

        assertEquals("RDULRDDRRD", task1(listOf("pxxbnzuo")))
    }

    @Test
    fun testTaskTwo() {
        assertEquals(370, task2(listOf("ihgpwlah")))
        assertEquals(492, task2(listOf("kglvqrro")))
        assertEquals(830, task2(listOf("ulqzkmiv")))

        assertEquals(752, task2(listOf("pxxbnzuo")))
    }

    private fun readPuzzle(): List<String> {
        val path = Paths.get(this.javaClass.getResource("/day17_puzzle_input.txt").toURI())
        val readAllLines = Files.readAllLines(path)
        return readAllLines
    }
}