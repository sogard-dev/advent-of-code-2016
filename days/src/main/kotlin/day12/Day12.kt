package day12

import java.util.*

fun task1(input: List<String>): Int {
    val memory = Memory()
    val instruction = parse(input)

    while (memory.programCounter in instruction.indices) {
        instruction[memory.programCounter].execute(memory)
    }

    return memory.get(Register.A)
}

private fun parse(input: List<String>): Array<Instruction> {
    return input.map {
        val split = it.split(" ")

        when (split[0]) {
            "inc" -> INC(getRegister(split[1]))
            "dec" -> DEC(getRegister(split[1]))
            "cpy" -> CPY(getValue(split[1]), getRegister(split[2]))
            "jnz" -> JNZ(getRegister(split[1]), getNumber(split[2]))
            else -> throw UnsupportedOperationException()
        }
    }.toTypedArray()
}

fun task2(input: List<String>): Int {
    return -1
}

private class Memory(var programCounter: Int = 0) {
    private val memory: EnumMap<Register, Int> = EnumMap(day12.Register::class.java)
    fun set(register: Register, num: Int) {
        memory[register] = num
    }

    fun incPC() {
        programCounter++
    }

    fun get(register: Register): Int {
        return memory[register] ?: 0
    }

    fun setDeltaPc(jump: Int) {
        programCounter += jump
    }

}

private fun getValue(inp: String): Value {
    val toIntOrNull = inp.toIntOrNull()
    if (toIntOrNull != null) {
        return NumberValue(toIntOrNull)
    }
    return RegisterReference(Register.valueOf(inp.uppercase()))
}

private fun getRegister(inp: String): Register {
    return Register.valueOf(inp.uppercase())
}

private fun getNumber(inp: String): Int {
    return inp.toIntOrNull()!!
}

private sealed interface Value
private class NumberValue(val value: Int) : Value
private class RegisterReference(val register: Register) : Value


private sealed interface Instruction {
    fun execute(memory: Memory)
}

private class CPY(val value: Value, val register: Register) : Instruction {
    override fun execute(memory: Memory) {
        when (value) {
            is NumberValue -> memory.set(register, value.value)
            is RegisterReference -> memory.set(register, memory.get(value.register))
        }
        memory.incPC()
    }
}

private class JNZ(val register: Register, val jump: Int) : Instruction {
    override fun execute(memory: Memory) {
        if (memory.get(register) != 0) {
            memory.setDeltaPc(jump)
        } else {
            memory.incPC()
        }
    }
}

private class INC(val register: Register) : Instruction {
    override fun execute(memory: Memory) {
        memory.set(register, memory.get(register) + 1)
        memory.incPC()
    }
}

private class DEC(val register: Register) : Instruction {
    override fun execute(memory: Memory) {
        memory.set(register, memory.get(register) - 1)
        memory.incPC()
    }
}


private enum class Register { A, B, C, D }