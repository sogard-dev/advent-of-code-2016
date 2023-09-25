package day7

class Day7 {
    companion object {
        private fun isABBA(input: String) = input.windowed(4).any { it[0] == it[3] && it[1] == it[2] && it[0] != it[1] }

        private fun isABA(s: String) = s[0] == s[2] && s[1] != s[0]

        fun task1(input: List<String>): Int {
            return input.map(Companion::supportTls).count { it }
        }

        fun task2(input: List<String>): Int {
            return input.map(Companion::supportSsl).count { it }
        }

        fun supportTls(input: String): Boolean {
            val (supernet, hypernet) = splitInput(input)

            if (hypernet.any { isABBA(it) }) {
                return false;
            }

            return supernet.any { isABBA(it) }
        }

        fun supportSsl(input: String): Boolean {
            val (supernet, hypernet) = splitInput(input)

            val abas = supernet.flatMap {
                it.windowed(3).filter { s -> isABA(s) }
            }.toSet()

            return hypernet.any {
                it.windowed(3).any { s ->
                    if (isABA(s)) {
                        val inverse = "".plus(s[1]).plus(s[0]).plus(s[1])
                        abas.contains(inverse)
                    } else {
                        false
                    }
                }
            }
        }

        private fun splitInput(input: String): Pair<ArrayList<String>, ArrayList<String>> {
            val supernet = ArrayList<String>()
            val hypernet = ArrayList<String>()

            input.splitToSequence("[", "]").forEachIndexed { i, s ->
                if (i % 2 == 0) {
                    supernet.add(s);
                } else {
                    hypernet.add(s)
                }
            }

            return Pair(supernet, hypernet)
        }
    }
}