package day12

import java.util.*

fun task1(input: List<String>, registerA: Int = 0): Int {
    val memory = Memory()
    memory.set(Register.A, registerA)

    var instructions = parse(input)

    while (memory.programCounter in instructions.indices) {
        instructions = instructions[memory.programCounter].execute(memory, instructions)
    }

    return memory.get(Register.A)
}

fun task2(input: List<String>): Int {
    val memory = Memory()
    memory.set(Register.C, 1)
    var instructions = parse(input)

    while (memory.programCounter in instructions.indices) {
        instructions = instructions[memory.programCounter].execute(memory, instructions)
    }

    return memory.get(Register.A)
}

private fun parse(input: List<String>): Array<Instruction> {
    return input.map {
        val split = it.split(" ")

        when (split[0]) {
            "inc" -> INC(getRegister(split[1]))
            "dec" -> DEC(getRegister(split[1]))
            "tgl" -> TGL(getRegister(split[1]))
            "cpy" -> CPY(getValue(split[1]), getValue(split[2]))
            "jnz" -> JNZ(getValue(split[1]), getValue(split[2]))
            else -> throw UnsupportedOperationException()
        }
    }.toTypedArray()
}

private data class Memory(var programCounter: Int = 0) {
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
private data class NumberValue(val value: Int) : Value
private data class RegisterReference(val register: Register) : Value


private sealed interface Instruction {
    fun execute(memory: Memory, instructions: Array<Instruction>): Array<Instruction>
}

private data class CPY(val value: Value, val target: Value) : Instruction {
    override fun execute(memory: Memory, instructions: Array<Instruction>): Array<Instruction> {
        if (target is RegisterReference) {
            when (value) {
                is NumberValue -> memory.set(target.register, value.value)
                is RegisterReference -> memory.set(target.register, memory.get(value.register))
            }
        }

        memory.incPC()
        return instructions
    }
}

private data class JNZ(val testVar: Value, val jump: Value) : Instruction {
    override fun execute(memory: Memory, instructions: Array<Instruction>): Array<Instruction> {
        val value = when (testVar) {
            is NumberValue -> testVar.value
            is RegisterReference -> memory.get(testVar.register)
        }

        if (value != 0) {
            val delta = when(jump) {
                is NumberValue -> jump.value
                is RegisterReference -> memory.get(jump.register)
            }

            memory.setDeltaPc(delta)
        } else {
            memory.incPC()
        }

        return instructions
    }
}

private data class INC(val register: Register) : Instruction {
    override fun execute(memory: Memory, instructions: Array<Instruction>): Array<Instruction> {
        memory.set(register, memory.get(register) + 1)
        memory.incPC()

        return instructions
    }
}

private data class DEC(val register: Register) : Instruction {
    override fun execute(memory: Memory, instructions: Array<Instruction>): Array<Instruction> {
        memory.set(register, memory.get(register) - 1)
        memory.incPC()

        return instructions
    }
}

private data class TGL(val register: Register) : Instruction {
    override fun execute(memory: Memory, instructions: Array<Instruction>): Array<Instruction> {
        val jump = memory.get(register)
        val idx = memory.programCounter + jump
        memory.incPC()

        if (idx in instructions.indices) {
            val newIns = when (val ins = instructions[idx]) {
                is INC -> DEC(ins.register)
                is DEC -> INC(ins.register)
                is TGL -> INC(ins.register)
                is JNZ -> CPY(ins.testVar, ins.jump)
                is CPY -> JNZ(ins.value, ins.target)
            }

            val copyOf = instructions.copyOf()
            copyOf[idx] = newIns
            return copyOf
        }

        return instructions
    }
}


private enum class Register { A, B, C, D }