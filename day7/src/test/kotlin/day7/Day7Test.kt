package day7

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day7Test {

    @Test
    fun testTaskOne() {
        assertEquals(false, Day7.supportTls("bjryqckxvymkcdydn[nqivnqzbjhreueaajna]fxpfigwhtxixllsir[pkushhryhehrccy]xishkltxvbfsxhkling[kulvofivcvexawp]soiyukxfuwwdgccug"))
        assertEquals(true, Day7.supportTls("abba[mnop]qrst"))
        assertEquals(false, Day7.supportTls("abcd[bddb]xyyx"))
        assertEquals(false, Day7.supportTls("aaaa[qwer]tyui"))
        assertEquals(true, Day7.supportTls("ioxxoj[asdfgh]zxcvbn"))

        assertEquals(110, Day7.task1(readPuzzle()))
    }

    @Test
    fun testTaskTwo() {
        assertEquals(true, Day7.supportSsl("cfndlwialijdidbo[ncypuoyqemkzhwoio]mebhpaowwzrzfarmrw[wgjkjwbohgrovurdcf]tdaitjcszcmisetz[esfxareaykafawe]achdtioidaxwmeguzu"))
        assertEquals(true, Day7.supportSsl("aba[bab]xyz"))
        assertEquals(false, Day7.supportSsl("xyx[xyx]xyx"))
        assertEquals(true, Day7.supportSsl("aaa[kek]eke"))
        assertEquals(true, Day7.supportSsl("zazbz[bzb]cdb"))

        assertEquals(242, Day7.task2(readPuzzle()))
    }

    private fun readPuzzle(): List<String> {
        val path = Paths.get(this.javaClass.getResource("/puzzle_input.txt").toURI())
        val readAllLines = Files.readAllLines(path)
        return readAllLines
    }
}