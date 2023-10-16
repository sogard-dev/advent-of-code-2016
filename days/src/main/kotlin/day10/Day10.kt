package day10

import kotlin.math.max
import kotlin.math.min

fun task1(input: List<String>, a: Int, b: Int): Int {
    val (robots, _) = run(input)

    return robots.entries.find {
        it.value.peak() == Pair(a, b)
    }?.key ?: -1
}

fun task2(input: List<String>): Int {
    val (_, outputs) = run(input)

    return outputs.getValue(0).list[0] * outputs.getValue(1).list[0] * outputs.getValue(2).list[0]
}

private fun run(input: List<String>): Pair<HashMap<Int, RobotInstance>, HashMap<Int, BinInstance>> {
    val robots = HashMap<Int, RobotInstance>()
    val outputs = HashMap<Int, BinInstance>()

    for (s in input) {
        when (val instruction = parse(s)) {
            is GivingInstruction -> {
                val robot = robots.getOrPut(instruction.fromBot) { RobotInstance() }

                when (val lowReceiver = instruction.lowReceiver) {
                    is Robot -> robot.addLowChild(robots.getOrPut(lowReceiver.bot) { RobotInstance() })
                    is Bin -> robot.addLowChild(outputs.getOrPut(lowReceiver.bin) { BinInstance() })
                }

                when (val highReceiver = instruction.highReceiver) {
                    is Robot -> robot.addHighChild(robots.getOrPut(highReceiver.bot) { RobotInstance() })
                    is Bin -> robot.addHighChild(outputs.getOrPut(highReceiver.bin) { BinInstance() })
                }
            }
        }
    }

    for (s in input) {
        when (val instruction = parse(s)) {
            is ValueInstruction -> {
                robots.getOrPut(instruction.bot) { RobotInstance() }.giveValue(instruction.value)
            }
        }
    }

    return Pair(robots, outputs)
}

private interface CoinLower {
    fun giveValue(value: Int)
}

private class BinInstance : CoinLower {
    val list: MutableList<Int> = mutableListOf()


    override fun giveValue(value: Int) {
        list.add(value)
    }
}

private class RobotInstance : CoinLower {

    var lowIs = -1
    var highIs = -1
    var lowChild: CoinLower? = null
    var highChild: CoinLower? = null

    override fun giveValue(value: Int) {
        if (lowIs == -1) {
            lowIs = value
        } else {
            highIs = max(lowIs, value)
            lowIs = min(lowIs, value)

            lowChild?.giveValue(lowIs)
            highChild?.giveValue(highIs)
        }
    }

    fun addLowChild(c: CoinLower) {
        lowChild = c
    }

    fun addHighChild(c: CoinLower) {
        highChild = c
    }

    fun peak(): Pair<Int, Int> {
        return Pair(lowIs, highIs)
    }
}


private interface Receiver
private class Robot(val bot: Int) : Receiver
private class Bin(val bin: Int) : Receiver

private interface Instruction
private class ValueInstruction(val value: Int, val bot: Int) : Instruction
private class GivingInstruction(val fromBot: Int, val lowReceiver: Receiver, val highReceiver: Receiver) : Instruction

private fun parse(inp: String): Instruction {
    val split = inp.split(" ")

    when (split[0]) {
        "value" -> {
            val value = split[1].toInt()
            val bot = split[5].toInt()

            return ValueInstruction(value, bot)
        }

        "bot" -> {
            val fromBot = split[1].toInt()
            val lowValueTo = split[6].toInt()

            val lowReceiver = when (split[5]) {
                "output" -> Bin(lowValueTo)
                "bot" -> Robot(lowValueTo)
                else -> throw RuntimeException()
            }

            val highValueTo = split[11].toInt()
            val highReceiver = when (split[10]) {
                "output" -> Bin(highValueTo)
                "bot" -> Robot(highValueTo)
                else -> throw RuntimeException()
            }

            return GivingInstruction(fromBot, lowReceiver, highReceiver)
        }
    }

    throw RuntimeException(inp)
}