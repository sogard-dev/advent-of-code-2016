package day21

import java.lang.IllegalArgumentException

fun task1(initial: String, input: List<String>): String {
    val instructions = parse(input)

    return executeInstructions(initial, instructions)
}

fun task2(target: String, input: List<String>): String {
    val instructions = parse(input)

    val allPermutations = target.toList().allPermutations()
    for (passwordArr in allPermutations) {
        val password = passwordArr.joinToString("")
        if (target == executeInstructions(password, instructions)) {
            return password
        }
    }

    throw IllegalArgumentException()
}

private fun executeInstructions(initial: String, instructions: List<Instruction>): String {
    var password = initial
    for (instruction in instructions) {
        password = execute(instruction, password)
    }

    return password
}

fun execute(instruction: Instruction, password: String): String {
    return when (instruction) {
        is ReverseSubset -> {
            val prefix = password.substring(0 until instruction.x)
            val infix = password.substring(instruction.x..instruction.y)
            val suffix = password.substring(instruction.y + 1)
            prefix + infix.reversed() + suffix
        }

        is RotateLeft -> {
            val div = instruction.steps % password.length
            val left = password.substring(0 until div)
            val right = password.substring(div)
            right + left
        }

        is RotateRight -> {
            val div = password.length - (instruction.steps % password.length)
            val left = password.substring(0 until div)
            val right = password.substring(div)
            right + left
        }

        is SwapPosition -> {
            val sb = StringBuilder(password)
            sb[instruction.x] = password[instruction.y]
            sb[instruction.y] = password[instruction.x]
            sb.toString()
        }

        is Move -> {
            val sb = StringBuilder(password)
            sb.deleteCharAt(instruction.x)
            sb.insert(instruction.y, password[instruction.x])
            sb.toString()
        }

        is SwapLetter -> {
            val a = password.indexOf(instruction.x)
            val b = password.indexOf(instruction.y)
            execute(SwapPosition(a, b), password)
        }

        is RotateRightBasedOnPosition -> {
            var a = 1 + password.indexOf(instruction.letter)
            if (a >= 5) a++
            execute(RotateRight(a), password)
        }
    }
}

private fun parse(input: List<String>): List<Instruction> {
    val parsers: List<Parser> = listOf(
        Parser("swap position (\\d+) with position (\\d+)") { SwapPosition(it[1]!!.value.toInt(), it[2]!!.value.toInt()) },
        Parser("swap letter (\\w) with letter (\\w)") { SwapLetter(it[1]!!.value[0], it[2]!!.value[0]) },
        Parser("rotate right (\\d+) step") { RotateRight(it[1]!!.value.toInt()) },
        Parser("rotate left (\\d+) step") { RotateLeft(it[1]!!.value.toInt()) },
        Parser("rotate based on position of letter (\\w)") { RotateRightBasedOnPosition(it[1]!!.value[0]) },
        Parser("reverse positions (\\d+) through (\\d+)") { ReverseSubset(it[1]!!.value.toInt(), it[2]!!.value.toInt()) },
        Parser("move position (\\d+) to position (\\d+)") { Move(it[1]!!.value.toInt(), it[2]!!.value.toInt()) },
    )

    return input.map { s -> parsers.find { p -> p.parse(s) != null }!!.parse(s)!! }
}

private class Parser(val pattern: String, val create: (MatchGroupCollection) -> Instruction) {

    fun parse(input: String): Instruction? {
        val find = Regex(pattern).find(input)
        if (find?.groups != null) {
            return create(find.groups)
        }
        return null
    }
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

sealed interface Instruction {}
data class SwapPosition(val x: Int, val y: Int) : Instruction
data class SwapLetter(val x: Char, val y: Char) : Instruction
data class RotateLeft(val steps: Int) : Instruction
data class RotateRight(val steps: Int) : Instruction
data class RotateRightBasedOnPosition(val letter: Char) : Instruction
data class ReverseSubset(val x: Int, val y: Int) : Instruction
data class Move(val x: Int, val y: Int) : Instruction

