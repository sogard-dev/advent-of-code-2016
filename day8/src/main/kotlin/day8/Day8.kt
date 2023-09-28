package day8

class Day8 {
    companion object {
        fun task1(input: List<String>): Int {
            val display = Display(50, 6)

            for (s in input) {
                val splitToSequence = s.splitToSequence(" ", "=", "x").asSequence().toList()
                when (splitToSequence[0]) {
                    "rect" -> display.rect(splitToSequence[1].toInt(), splitToSequence[2].toInt())
                    "rotate" -> {
                        when (splitToSequence[1]) {
                            "row" -> display.rotateRow(splitToSequence[3].toInt(), splitToSequence[5].toInt())
                            "column" -> display.rotateColumn(splitToSequence[4].toInt(), splitToSequence[6].toInt())
                        }
                    }
                }
            }


            display.print()

            return display.count()
        }

        fun task2(input: List<String>): Int = 2
    }

    class Display(private val width: Int, private val height: Int) {

        private var display = Array(height) { Array(width) { false } }

        fun count() : Int {
            return display.sumOf { a ->
                a.map  { b  ->
                    if (b) {
                        1
                    } else {
                        0
                    }
                }.sum()
            }
        }

        fun print() {
            for (ints in display) {
                for (b in ints) {
                    if (b) {
                        print("#")
                    } else {
                        print(".")
                    }
                }
                println()
            }
        }

        fun rect(width: Int, height: Int) {
            for (h in 0..<height) {
                for (w in 0..<width) {
                    display[h][w] = true;
                }
            }
        }

        fun toLine(): String {
            return display.joinToString("") { h ->
                h.joinToString("") { b ->
                    if (b) {
                        "#"
                    } else
                        "."
                }
            }
        }

        fun rotateColumn(x: Int, amt: Int) {
            val previousDisplay = display;

            display = Array(height) { Array(width) { false } }
            for (h in 0..<height) {
                for (w in 0..<width) {
                    if (w == x) {
                        display[(h + amt) % height][w] = previousDisplay[h][w];
                    } else {
                        display[h][w] = previousDisplay[h][w];
                    }
                }
            }
        }

        fun rotateRow(y: Int, amt: Int) {
            val previousDisplay = display;

            display = Array(height) { Array(width) { false } }
            for (h in 0..<height) {
                for (w in 0..<width) {
                    if (h == y) {
                        display[h][(w + amt) % width] = previousDisplay[h][w];
                    } else {
                        display[h][w] = previousDisplay[h][w];
                    }
                }
            }
        }
    }
}