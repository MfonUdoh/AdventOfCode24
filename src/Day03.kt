import java.util.Optional
import kotlin.math.min

fun main() {

    fun part1(input: List<String>): Int {
        return input
            .map { it: String -> getMuls(it).fold(0) { acc: Int, mul: Pair<Int, Int> -> acc + (mul.first * mul.second) } }
            .sum()
    }

    fun part2(input: List<String>): Int {
        val combined = input.joinToString("")
        return getMuls2(combined).fold(0) { acc: Int, mul: Pair<Int, Int> -> acc + (mul.first * mul.second) }
    }

    val testInput = readInput("Day03_test")
//    check(part1(testInput) == 161)
    check(part2(testInput) == 48)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}

fun getMuls(string: String): List<Pair<Int, Int>> {
    val result = mutableListOf<Pair<Int, Int>>()
    for (i in string.indices) {
        if (string[i] == 'm') {
            val left: Boolean = checkLeft(string, i)
            if (left) {
                val innerText = getInnerString(string, i)

                if (innerText != "") {
                    val pair = tryGetPair(innerText)
                    result.add(pair)
                }
            }
        }
    }
    return result
}

fun getMuls2(string: String): List<Pair<Int, Int>> {
    val result = mutableListOf<Pair<Int, Int>>()
    var enabled = true
    for (i in string.indices) {
        if (string[i] == 'm' && enabled) {
            val left: Boolean = checkLeft(string, i)
            if (left) {
                val innerText = getInnerString(string, i)

                if (innerText != "") {
                    val pair = tryGetPair(innerText)
                    result.add(pair)
                }
            }
        } else if (string[i] == 'd') {
            val instruction = checkInstruction(string, i)
            if (instruction.isPresent) {
                enabled = instruction.get()
            }
        }
    }
    return result
}

fun checkInstruction(string: String, i: Int): Optional<Boolean> {
    val doInstruction = string.substring(i, i + 4) == "do()"
    if (doInstruction) {
        return Optional.of(true)
    }
    val dontInstruction = string.substring(i, i + 7) == "don\'t()"
    if (dontInstruction) {
        return Optional.of(false)
    }
    return Optional.empty()
}

fun tryGetPair(innerText: String): Pair<Int, Int> {
    val result = innerText.split(",").map { it.toIntOrNull() ?: 0 }
    return if (result.size == 2) {
        Pair(result[0], result[1])
    } else Pair(0, 0)
}

fun getInnerString(string: String, i: Int): String {
    for (j in i + 4..(min(string.lastIndex, i + 12))) {
        if (string[j] == ')') {
            return string.substring(i + 4, j)
        }
    }
    return ""
}

fun checkLeft(string: String, i: Int): Boolean {
    return string.substring(i, i + 4) == "mul("
}

