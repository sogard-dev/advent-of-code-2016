package day16

fun buildNext(a: String): String {
    val b = a.reversed().replace('0', '2').replace('1', '0').replace('2', '1')
    return "${a}0${b}"
}

fun generate(initialState: String, len: Int): String {
    var state = initialState
    while (state.length < len) {
        state = buildNext(state)
    }
    return state.substring(0 until len)
}

fun checksum(s: String): String {
    if (s.length % 2 == 1) {
        return s
    }

    return checksum(s.windowedSequence(2, 2).joinToString(separator = "") {
        if (it[0] == it[1]) {
            "1"
        } else {
            "0"
        }
    })
}

fun task1(input: List<String>, len: Int): String {
    val state = input[0]
    return checksum(generate(state, len))
}

fun task2(input: List<String>, len: Int): String {
    return task1(input, len)
}