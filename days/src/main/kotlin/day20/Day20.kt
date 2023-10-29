package day20

import kotlin.math.max

fun task1(input: List<String>, validFrom: Long, validTo: Long): Long {
    val toList = input.map { it.split("-").map { n -> n.toLong() } }.map { Pair(it[0], it[1]) }.sortedBy { it.first }.toList()
    val merged = mergeRanges(toList)
    if (merged[0].first > validFrom) {
        return validFrom
    }
    return merged[0].second + 1
}

fun task2(input: List<String>, validFrom: Long, validTo: Long): Long {
    val toList = input.map { it.split("-").map { n -> n.toLong() } }.map { Pair(it[0], it[1]) }.sortedBy { it.first }.toList()
    val merged = mergeRanges(toList)
    val range = validTo - validFrom + 1
    val takenAlready = merged.sumOf { it.second - it.first + 1 }
    return range - takenAlready
}

fun mergeRanges(ranges: List<Pair<Long, Long>>): List<Pair<Long, Long>> {
    val mutList = ranges.toMutableList()
    var changed = true
    while (changed) {
        var i = 0;
        changed = false
        while (i < mutList.size - 1) {
            val cur = mutList[i]
            val next = mutList[i + 1]
            if (cur.second >= next.first - 1) {
                mutList[i] = Pair(cur.first, max(cur.second, next.second))
                mutList.removeAt(i + 1)
                changed = true
            }
            i++
        }
    }

    return mutList
}
