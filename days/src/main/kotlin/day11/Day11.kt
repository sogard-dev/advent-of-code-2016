package day11

import java.util.regex.Pattern

fun task1(input: List<String>): Int {
    val (items: MutableList<Item>, place: MutableList<Int>) = generateItemsAndFloors(input)
    val places = doWork(items.toTypedArray(), place.toIntArray())
    return places
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
    return places
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

private fun doWork(items: Array<Item>, initPlaces: IntArray): Int {
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

    fun isValidFloor(otherFloor: Int): Boolean {
        return otherFloor in lowestFloor..highestFloor
    }

    fun bfs(): Int {
        val queue: MutableList<Pair<Int, IntArray>> = mutableListOf()
        val nextRoundQueue: MutableList<Pair<Int, IntArray>> = mutableListOf()
        val seen = mutableSetOf<Pair<Int, List<Pair<Int, Int>>>>()

        queue.add(Pair(1, initPlaces))
        var moves = 0
        while (queue.isNotEmpty()) {
            val (elevator, places) = queue.removeFirst()
            val itemsOnFloor = places.withIndex().filter { (_, floorIndex) -> floorIndex == elevator }.toList()

            if (places.all { it == 4 }) {
                return moves
            }

            for (diff in arrayOf(1, -1)) {
                val movedToFloor = elevator + diff

                if (isValidFloor(movedToFloor)) {
                    val doubles = lazyCartesianProduct(itemsOnFloor)
                        .map { (a, b) ->
                            val newPlaces = places.clone()
                            newPlaces[a.index] = movedToFloor
                            newPlaces[b.index] = movedToFloor
                            newPlaces
                        }
                    val singles = itemsOnFloor.map { a ->
                        val newPlaces = places.clone()
                        newPlaces[a.index] = movedToFloor
                        newPlaces
                    }

                    (singles + doubles)
                        .filter { isSafe(it) }
                        .map { Pair(movedToFloor, it) }
                        .filter { seen.add(Pair(it.first, gensAndMicros(items, it.second))) }
                        .forEach { newPlaces ->
                            nextRoundQueue.add(newPlaces)
                        }
                }
            }

            if (queue.isEmpty()) {
                println("Trying moves of size $moves")
                moves++
                queue.addAll(nextRoundQueue)
                nextRoundQueue.clear()
            }
        }

        return Int.MAX_VALUE
    }

    return bfs()
}

private fun gensAndMicros(items: Array<Item>, second: IntArray): List<Pair<Int, Int>> {
    return (1..4).map {
        val itemsOnFloor = second.withIndex()
            .filter { (_, floor) -> floor == it }
        val gens = itemsOnFloor.count { items[it.index] is Generator }
        val micros = itemsOnFloor.count { items[it.index] is Microchip }

        Pair(gens, micros)
    }
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