package day24

typealias RC = Pair<Int, Int>


fun task1(input: List<String>): Int {
    val triple = prepareGraph(input)
    val mustVisit: MutableList<RC> = triple.first
    val start: RC = triple.second
    val distances: HashMap<Pair<RC, RC>, Int> = triple.third

    return mustVisit.indices.toList().allPermutations().minOf { order ->
        var total = 0
        var from = start

        order.forEach { idx ->
            val to = mustVisit[idx]
            total += distances[Pair(from, to)]!!
            from = to
        }

        total
    }
}


fun task2(input: List<String>): Int {
    val triple = prepareGraph(input)
    val mustVisit: MutableList<RC> = triple.first
    val start: RC = triple.second
    val distances: HashMap<Pair<RC, RC>, Int> = triple.third

    return mustVisit.indices.toList().allPermutations().minOf { order ->
        var total = 0
        var from = start

        order.forEach { idx ->
            val to = mustVisit[idx]
            total += distances[Pair(from, to)]!!
            from = to
        }

        total += distances[Pair(from, start)]!!
        total
    }
}

private fun prepareGraph(input: List<String>): Triple<MutableList<RC>, RC, HashMap<Pair<RC, RC>, Int>> {
    val walkable: MutableSet<RC> = HashSet()
    val mustVisit: MutableList<RC> = mutableListOf()
    var start: RC? = null

    input.withIndex().forEach { (r, line) ->
        line.withIndex().forEach { (c, letter) ->
            when (letter) {
                '0' -> {
                    start = RC(r, c)
                    walkable.add(RC(r, c))
                }

                in '1'..'9' -> {
                    mustVisit.add(RC(r, c))
                    walkable.add(RC(r, c))
                }

                '.' -> {
                    walkable.add(RC(r, c))
                }
            }
        }
    }

    val distances: HashMap<Pair<RC, RC>, Int> = HashMap()

    val rcOfInterest = mustVisit + start!!
    rcOfInterest.forEach {
        val openSet = mutableListOf(it)
        val nextSet: MutableList<RC> = mutableListOf()
        val closedSet: MutableSet<RC> = HashSet()
        closedSet.add(it)

        var travelled = 0
        while (openSet.isNotEmpty() || nextSet.isNotEmpty()) {
            travelled++

            while (openSet.isNotEmpty()) {
                val visitRC = openSet.removeFirst()
                for ((dr, dc) in listOf(RC(0, 1), RC(1, 0), RC(0, -1), RC(-1, 0))) {
                    val nextRc = RC(visitRC.first + dr, visitRC.second + dc)
                    if (walkable.contains(nextRc) && !closedSet.contains(nextRc)) {
                        closedSet.add(nextRc)
                        nextSet.add(nextRc)

                        if (rcOfInterest.contains(nextRc)) {
                            distances[Pair(it, nextRc)] = travelled
                        }
                    }
                }
            }

            openSet.addAll(nextSet)
            nextSet.clear()
        }
    }
    return Triple(mustVisit, start!!, distances)
}


fun <T> List<T>.allPermutations(): Sequence<List<T>> {
    if (isEmpty()) return sequenceOf(emptyList())
    val list = this
    return indices
        .asSequence()
        .flatMap { i ->
            val elem = list[i]
            (list - elem).allPermutations().map { perm -> perm + elem }
        }
}