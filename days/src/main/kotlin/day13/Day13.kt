package day13

fun task1(input: List<String>, column: Int, row: Int): Int {
    val favNumber = input[0].toInt()

    val seen: HashSet<Pair<Int, Int>> = HashSet()
    val queue: MutableList<Pair<Int, Int>> = mutableListOf()
    val nextQueue: MutableList<Pair<Int, Int>> = mutableListOf()

    queue.add(Pair(1, 1))
    seen.add(Pair(1, 1))

    var moves = 0
    val options = listOf(Pair(1, 0), Pair(0, 1), Pair(-1, 0), Pair(0, -1))
    while (queue.isNotEmpty()) {
        val (c, r) = queue.removeFirst()
        if (c == column && r == row) {
            return moves
        }
        options
            .map { Pair(it.first + c, it.second + r) }
            .filter { it.first >= 0 && it.second >= 0 }
            .filter { area(it.first, it.second, favNumber) == Type.Open }
            .filter { seen.add(it) }
            .forEach { nextQueue.add(it) }

        if (queue.isEmpty()) {
            moves++
            queue.addAll(nextQueue)
            nextQueue.clear()
        }
    }

    return -1
}

private fun area(column: Int, row: Int, fav: Int): Type {
    val num = (column * column + 3 * column + 2 * column * row + row + row * row) + fav

    return when (num.countOneBits() % 2) {
        0 -> Type.Open
        else -> Type.Wall
    }
}

private enum class Type {
    Wall, Open
}

fun task2(input: List<String>, max: Int): Int {
    val favNumber = input[0].toInt()

    val seen: HashSet<Pair<Int, Int>> = HashSet()
    val queue: MutableList<Pair<Int, Int>> = mutableListOf()
    val nextQueue: MutableList<Pair<Int, Int>> = mutableListOf()

    queue.add(Pair(1, 1))
    seen.add(Pair(1, 1))

    var moves = 0
    val options = listOf(Pair(1, 0), Pair(0, 1), Pair(-1, 0), Pair(0, -1))
    while (queue.isNotEmpty()) {
        val (c, r) = queue.removeFirst()
        if (moves == max) {
            return seen.size
        }

        options
            .map { Pair(it.first + c, it.second + r) }
            .filter { it.first >= 0 && it.second >= 0 }
            .filter { area(it.first, it.second, favNumber) == Type.Open }
            .filter { seen.add(it) }
            .forEach { nextQueue.add(it) }

        if (queue.isEmpty()) {
            moves++
            queue.addAll(nextQueue)
            nextQueue.clear()
        }
    }

    return -1
}