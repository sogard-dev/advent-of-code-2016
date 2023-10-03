package day9

import java.util.regex.Pattern

class Day9 {
    companion object {
        fun task1(inputs: List<String>): Int {
            val input = inputs[0]
            val compile = Pattern.compile(("\\(([\\d]+)x([\\d]+)\\)"))

            var pointer = 0;
            var length = 0;
            while (pointer < input.length) {
                val matcher = compile.matcher(input.substring(pointer))
                if (matcher.find()) {
                    val end = matcher.end()
                    val charCount = matcher.group(1).toInt()
                    val repeat = matcher.group(2).toInt()

                    pointer += end + charCount;
                    length += (charCount * repeat) + matcher.start()
                } else {
                    length += input.length - pointer
                    pointer += input.length - pointer
                }
            }

            return length
        }

        fun task2(inputs: List<String>): Long {
            val input = inputs[0]
            val values = Array(input.length) { 1 }
            val compile = Pattern.compile(("\\(([\\d]+)x([\\d]+)\\)"))

            val matcher = compile.matcher(input)
            var pointer = 0
            var sum = 0L
            while (matcher.find()) {
                for (i in pointer until matcher.start()) {
                    sum += values[i]
                }
                val end = matcher.end()
                val charCount = matcher.group(1).toInt()
                val repeat = matcher.group(2).toInt()
                for (i in end until end + charCount) {
                    values[i] *= repeat
                }

                pointer = end;
            }
            for (i in pointer until input.length) {
                sum += values[i]
            }

            return sum
        }
    }
}