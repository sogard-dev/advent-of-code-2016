package day16

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day16Test {

    @Test
    fun testTaskOne() {
        assertEquals("100", buildNext("1"))
        assertEquals("001", buildNext("0"))
        assertEquals("11111000000", buildNext("11111"))
        assertEquals("1111000010100101011110000", buildNext("111100001010"))
        assertEquals("100", checksum("110010110100"))
        assertEquals("01100", task1(listOf("10000"), 20))

        assertEquals("11100111011101111", task1(listOf("01110110101001000"), 272))
    }

    @Test
    fun testTaskTwo() {
        assertEquals("10001110010000110", task2(listOf("01110110101001000"), 35651584))
    }
}