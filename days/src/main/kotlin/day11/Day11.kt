package day11

import java.util.regex.Pattern
import kotlin.math.min

fun task1(input: List<String>): Int {
    val mc = Pattern.compile("([a-z]+)-compatible microchip")
    val gen = Pattern.compile("([a-z]+) generator")

    val items: MutableList<Item> = mutableListOf() // Column -> Item
    val place: MutableList<Int> = mutableListOf() //Column -> Floor
    for ((floor, line) in input.withIndex()) {
        val mcMatcher = mc.matcher(line)
        while (mcMatcher.find()) {
            items.add(Microchip(mcMatcher.group(1)))
            place.add(floor + 1)
        }

        val genMatcher = gen.matcher(line)
        while (genMatcher.find()) {
            items.add(Generator(genMatcher.group(1)))
            place.add(floor + 1)
        }
    }

    return doWork(items.toTypedArray(), place.toIntArray())
}

private fun doWork(items: Array<Item>, initPlaces: IntArray): Int {
    val lowestFloor = 1
    val highestFloor = 4

    fun isDangerous(itemIndexes: List<Int>): Boolean {
        val itemsOnFloor = itemIndexes.map { items[it] }
        val generators = itemsOnFloor.filter { it is Generator }
        val microchips = itemsOnFloor.filter { it is Microchip }

        val generatorsWithMicrochips = generators.filter { gen -> microchips.any { micro -> micro.itemName() == gen.itemName() } }.map { it.itemName() }

        val generatorsLeft = generators.filter { !generatorsWithMicrochips.contains(it.itemName()) }
        val microchipsLeft = microchips.filter { !generatorsWithMicrochips.contains(it.itemName()) }

        if (generatorsLeft.isEmpty()) {
            return false
        }

        return microchipsLeft.isNotEmpty()
    }

    fun isSafe(places: IntArray): Boolean {
        val groupBy = places
            .withIndex()
            .groupBy { (_, value) -> value }

        return groupBy.none { (_, itemsIndexes) ->
            isDangerous(itemsIndexes.map { k -> k.index })
        }
    }

    fun canBeMovedTo(otherFloor: Int, itemIndex: Int, places: IntArray): Boolean {
        val newPlaces = places.clone()
        newPlaces[itemIndex] = otherFloor
        return isSafe(newPlaces)
    }

    fun canBeMovedTo(otherFloor: Int, itemIndex1: Int, itemIndex2: Int, places: IntArray): Boolean {
        val newPlaces = places.clone()
        newPlaces[itemIndex1] = otherFloor
        newPlaces[itemIndex2] = otherFloor
        return isSafe(newPlaces)
    }

    fun canMove(itemIndex: Int, places: IntArray) = canBeMovedTo(0, itemIndex, places)

    fun canMove(itemIndex1: Int, itemIndex2: Int, places: IntArray) = canBeMovedTo(0, itemIndex1, itemIndex2, places)

    fun isValidFloor(otherFloor: Int): Boolean {
        return otherFloor in lowestFloor..highestFloor
    }

    val cache = HashMap<Pair<Int, List<Int>>, Int>()
    var bestFound = Int.MAX_VALUE

    //Return steps to success
    fun recurse(places: IntArray, steps: Int, currentFloor: Int): Int {
        //- For all combinations of 1-2 items
        //- Find floors they can be safely moved to
        //- Move them
        var best = Int.MAX_VALUE

        if (steps > 12 || bestFound < steps) {
            return best
        }

        if (places.all { it == highestFloor }) {
            println("Found a path with steps: $steps")
            bestFound = min(bestFound, steps)
            return steps
        }

        val previous = cache.put(Pair(currentFloor, places.asList()), steps) ?: Int.MAX_VALUE
        if (previous <= steps) {
            return best
        }

        for (diff in arrayOf(1, -1)) {
            val movedToFloor = currentFloor + diff

            if (isValidFloor(movedToFloor)) {
                val itemsOnFloor = places.withIndex().filter { (_, floorIndex) -> floorIndex == currentFloor }.toList()

                lazyCartesianProduct(itemsOnFloor)
                    .filter { (a, b) -> canBeMovedTo(movedToFloor, a.index, b.index, places) }
                    .forEach { (a, b) ->
                        val newPlaces = places.clone()
                        newPlaces[a.index] = movedToFloor
                        newPlaces[b.index] = movedToFloor
                        best = min(best, recurse(newPlaces, steps + 1, movedToFloor))
                    }

                itemsOnFloor
                    .filter { (itemIndex, _) -> canBeMovedTo(movedToFloor, itemIndex, places) }
                    .forEach { (itemIndex, _) ->
                        val newPlaces = places.clone()
                        newPlaces[itemIndex] = movedToFloor
                        best = min(best, recurse(newPlaces, steps + 1, movedToFloor))
                    }
            }
        }

        return best
    }


    return recurse(initPlaces, 0, 1)
}


fun task2(input: List<String>): Int {
    return -1
}

private fun <A> lazyCartesianProduct(listA: List<A>): Sequence<Pair<A, A>> =
    sequence {
        for (i in listA.indices) {
            for (j in i + 1 until listA.size) {
                yield(listA[i] to listA[j])

            }
        }
    }

private sealed interface Item {
    fun itemName(): String
}

private class Generator(val name: String) : Item {
    override fun itemName() = name
}

private class Microchip(val name: String) : Item {
    override fun itemName() = name
}