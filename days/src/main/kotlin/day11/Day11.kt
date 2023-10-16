package day11

import java.util.regex.Pattern
import kotlin.math.min

fun task1(input: List<String>): Int {
    val (items: MutableList<Item>, place: MutableList<Int>) = generateItemsAndFloors(input)
    val places = doWork(items.toTypedArray(), place.toIntArray())
    print(items.toTypedArray(), places)
    return places.size - 1
}

fun task2(input: List<String>): Int {
    val (items: MutableList<Item>, place: MutableList<Int>) = generateItemsAndFloors(input)
    items.add(Microchip("elerium"))
    items.add(Generator("elerium"))
    items.add(Microchip("dilithium"))
    items.add(Generator("dilithium"))
    place.add(1)
    place.add(1)
    place.add(1)
    place.add(1)
    val places = doWork(items.toTypedArray(), place.toIntArray())
    print(items.toTypedArray(), places)
    return places.size - 1
}

private fun generateItemsAndFloors(input: List<String>): Pair<MutableList<Item>, MutableList<Int>> {
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
    return Pair(items, place)
}

private fun print(items: Array<Item>, moves: List<Pair<IntArray, Int>>) {
    //F4 .  .  .  .  .
    //F3 .  .  .  LG .
    //F2 E  HG HM .  .
    //F1 .  .  .  .  LM

    for ((places, floor) in moves) {
        for (floorIndex in 4 downTo 1) {
            val elevator = if (floor == floorIndex) "E  " else ".  "

            print("F$floorIndex $elevator")

            for ((itemIndex, item) in items.withIndex()) {
                val str = if (places[itemIndex] == floorIndex) "${item.shortName()} " else ".  "
                print(str)
            }
            println()
        }

        println()
    }

}

private fun doWork(items: Array<Item>, initPlaces: IntArray): List<Pair<IntArray, Int>> {
    val lowestFloor = 1
    val highestFloor = 4

    fun isFloorDangerous(itemIndexes: List<Int>): Boolean {
        val itemsOnFloor = itemIndexes.map { items[it] }
        val generators = itemsOnFloor.filterIsInstance<Generator>()

        if (generators.isEmpty()) {
            return false
        }

        val microchips = itemsOnFloor.filterIsInstance<Microchip>()
        val generatorsWithMicrochips = generators
            .filter { gen -> microchips.any { micro -> micro.itemName() == gen.itemName() } }
            .map { it.itemName() }

        val microchipsLeft = microchips.filter { !generatorsWithMicrochips.contains(it.itemName()) }

        return microchipsLeft.isNotEmpty()
    }

    fun isSafe(places: IntArray): Boolean {
        val groupBy = places
            .withIndex()
            .groupBy { (_, value) -> value }

        return groupBy.none { (_, itemsIndexes) ->
            isFloorDangerous(itemsIndexes.map { k -> k.index })
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

    fun isValidFloor(otherFloor: Int): Boolean {
        return otherFloor in lowestFloor..highestFloor
    }

    val cache = HashMap<Pair<Int, List<Int>>, Int>()
    var bestFound = Int.MAX_VALUE

    val currentMoves: MutableList<Pair<IntArray, Int>> = mutableListOf()

    var bestMoves: List<Pair<IntArray, Int>> = listOf()

    //Return steps to success
    fun recurse(places: IntArray, steps: Int, currentFloor: Int): Int {
        var best = Int.MAX_VALUE

        if (steps > 300 || bestFound < steps) {
            return best
        }

        val atLeastLeft = places.sumOf { (highestFloor - it) * 2 } - 8
        if (atLeastLeft + steps > bestFound) {
            return best
        }

        if (places.all { it == highestFloor }) {
            println("Found a path with steps: $steps")
            if (steps < bestFound) {
                bestFound = min(bestFound, steps)
                bestMoves = currentMoves.toList()

            }
            return steps
        }


        val cacheKey = Pair(currentFloor, places.asList())
        val previous = cache[cacheKey] ?: Int.MAX_VALUE
        if (previous <= steps) {
            return best
        }
        cache[cacheKey] = steps

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
                        currentMoves.add(Pair(newPlaces, movedToFloor))
                        best = min(best, recurse(newPlaces, steps + 1, movedToFloor))
                        currentMoves.removeLast()
                    }

                itemsOnFloor
                    .filter { (itemIndex, _) -> canBeMovedTo(movedToFloor, itemIndex, places) }
                    .forEach { (itemIndex, _) ->
                        val newPlaces = places.clone()
                        newPlaces[itemIndex] = movedToFloor
                        currentMoves.add(Pair(newPlaces, movedToFloor))
                        best = min(best, recurse(newPlaces, steps + 1, movedToFloor))
                        currentMoves.removeLast()
                    }
            }
        }

        return best
    }

    currentMoves.add(Pair(initPlaces, 1))
    recurse(initPlaces, 0, 1)

    return bestMoves
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
    fun shortName(): String
}

private class Generator(val name: String) : Item {
    override fun itemName() = name
    override fun shortName(): String {
        return name.substring(0, 1).uppercase() + "G"
    }
}

private class Microchip(val name: String) : Item {
    override fun itemName() = name
    override fun shortName(): String {
        return name.substring(0, 1).uppercase() + "M"
    }
}