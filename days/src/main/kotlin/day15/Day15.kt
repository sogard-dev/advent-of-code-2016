package day15

fun task1(input: List<String>): Int {
    val discs = parse(input)
    return solve(discs)
}

fun task2(input: List<String>): Int {
    val discs = parse(input).toMutableList()
    discs.add(Disc(11, 0, 0))
    return solve(discs)
}

private fun parse(input: List<String>): List<Disc> {
    val numbers = Regex("(\\d+)")
    val discs = input.map {
        val vals = numbers.findAll(it).map { q -> q.value.toInt() }.toList()
        Disc(vals[1], vals[2], vals[3])
    }
    return discs
}

private fun solve(discs: List<Disc>): Int {
    for (time in 0..Int.MAX_VALUE) {
        if (discs.withIndex().all { it.value.positionAtTime(time + it.index + 1) == 0 }) {
            return time
        }
    }

    return -1
}

private class Disc(val nPositions: Int, val atTime: Int, val startPosition: Int) {
    fun positionAtTime(time: Int) = (startPosition + time) % nPositions
}