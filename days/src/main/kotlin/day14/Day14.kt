package day14

import java.security.MessageDigest
import java.util.regex.Pattern


fun task1(input: List<String>): Int {
    val salt = input[0]
    return solve(salt)
}

fun task2(input: List<String>): Int {
    val salt = input[0]
    return solve(salt, 2016)
}

private fun solve(salt: String, hashOfHash: Int = 0): Int {
    val md = MessageDigest.getInstance("MD5")

    val triplets: HashMap<String, MutableList<Int>> = HashMap()

    val threePattern = Regex("(.)\\1{2}")
    val fivePattern = Regex("(.)\\1{4}")

    val pads: MutableList<Int> = mutableListOf()

    var continueUntil = 1000000
    for (i in 0 until continueUntil) {
        var md5 = md.digest((salt + i).toByteArray()).toHex()
        for(h in 0 until hashOfHash) {
            md5 = md.digest(md5.toByteArray()).toHex()
        }
        val find = threePattern.find(md5)
        if (find != null) {
            triplets.getOrPut(find.value) { mutableListOf() }.add(i)
        }

        fivePattern.findAll(md5).forEach {
            triplets[it.value.substring(0, 3)]?.forEach { hit ->
                val diff = i - hit
                if (diff in 1..1001 && !pads.contains(hit)) {
                    pads.add(hit)

                    if (pads.size == 64) {
                        continueUntil = i + 1000
                    }
                }
            }
        }

        if (i == continueUntil) {
            break
        }
    }

    pads.sort()

    return pads[63]
}

fun ByteArray.toHex(): String = joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }