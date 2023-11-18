package day22

fun task1(input: List<String>): Int {
    val nodes = input
        .filter { it.startsWith("/dev/") }
        .map { parse(it) }

    var count = 0
    for (a in nodes) {
        for (b in nodes) {
            if (a != b) {
                if (b.used > 0 && a.available >= b.used) {
                    count++
                }
            }
        }
    }
    return count
}

fun task2(input: List<String>, boundary : Int): Int {
    val inputNodes = input
        .filter { it.startsWith("/dev/") }
        .map { parse(it) }

    val maxX = inputNodes.maxOfOrNull { it.x }!!
    val maxY = inputNodes.maxOfOrNull { it.y }!!
    var shouldStop = Pair(1, 0)
    var dataOfInterest = Pair(maxX, 0)
    var emptySpace = Pair(inputNodes.find { it.used == 0 }!!.x, inputNodes.find { it.used == 0 }!!.y)

    val nodes: HashMap<Pair<Int, Int>, GridNode> = HashMap()
    inputNodes.forEach {
        nodes[Pair(it.x, it.y)] = GridNode(it.used, it.available)
    }

    printNodes(maxY, maxX, nodes, boundary)

    fun canMoveUp() = emptySpace.second > 0 && nodes[Pair(emptySpace.first, emptySpace.second - 1)]!!.used < 100
    fun canMoveRight() = emptySpace.first < maxX

    var moves = 0
    while (canMoveUp()) {
        moves++
        emptySpace = Pair(emptySpace.first, emptySpace.second - 1)
    }

    if (emptySpace.second != 0) {
        while (!canMoveUp()) {
            moves++
            emptySpace = Pair(emptySpace.first - 1, emptySpace.second)
        }

        while (canMoveUp()) {
            moves++
            emptySpace = Pair(emptySpace.first, emptySpace.second - 1)
        }
    }

    while (canMoveRight()) {
        moves++
        emptySpace = Pair(emptySpace.first + 1, emptySpace.second)
    }

    while (emptySpace != shouldStop) {
        //down, left, left, up, right
        moves += 5
        emptySpace = Pair(emptySpace.first - 1, emptySpace.second)
    }

    return moves
}

private fun printNodes(maxY: Int, maxX: Int, nodes: HashMap<Pair<Int, Int>, GridNode>, boundary: Int) {
    for (y in 0..maxY) {
        for (x in 0..maxX) {
            val n = nodes[Pair(x, y)]!!
            val total = n.available + n.used
            val str = when {
                x == 0 && y == 0 -> "S"
                x == maxX && y == 0 -> "T"
                n.used == 0 -> "_"
                total < boundary -> "."
                total > boundary -> "#"
                else -> "?"

            }
            print(str)
        }
        println()
    }
}

data class GridNode(val used: Int, val available: Int)

private fun parse(line: String): Node {
    val regex = Regex("/dev/grid/node-x(\\d+)-y(\\d+)\\s+(\\d+)T\\s+(\\d+)T\\s+(\\d+)T\\s+(\\d+)%")

    val groups = regex.find(line)!!.groups.toList()
    val argLIst = groups.asSequence().drop(1).map { it!!.value.toInt() }.toList()
    val (x, y, size, used, available) = argLIst
    //Filesystem              Size  Used  Avail  Use%
    ///dev/grid/node-x0-y0     86T   73T    13T   84%

    return Node(x, y, size, used, available)
}

data class Node(val x: Int, val y: Int, val size: Int, val used: Int, val available: Int)