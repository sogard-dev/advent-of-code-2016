package day19


fun task1(input: List<String>): Int {
    val numberOfElves = input[0].toInt()
    var elves = (1..numberOfElves).map { Pair(it, 1) }.toMutableList()
    while (elves.size > 1) {
        val removeFirst = elves.size % 2 == 1
        elves = elves.filterIndexed { a, _ -> a % 2 == 0 }.toMutableList()
        if (removeFirst) {
            elves.removeAt(0)
        }
    }

    return elves[0].first
}

fun task2(input: List<String>): Int {
    val numberOfElves = input[0].toInt()
    val elves = (1..numberOfElves).map { Pair(it, 1) }.toMutableList()
    var elfPointer = 0
    while (elves.size > 1) {
        if (elfPointer >= elves.size) {
            elfPointer = 0
        }
        val toRemove = (elfPointer + elves.size / 2) % elves.size
        elves.removeAt(toRemove)
        if (toRemove > elfPointer) {
            elfPointer++
        }
    }

    return elves[0].first
}