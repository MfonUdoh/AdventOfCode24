import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val pairs = input
            .map { it.split("   ") }
            .map { Pair(it[0], it[1]) }

        val left = pairs.map { it.first.toInt() }.sortedBy { it }
        val right = pairs.map { it.second.toInt() }.sortedBy { it }

        return left.mapIndexed { index, it -> abs(right[index] - it) }.fold(0) { acc, i -> acc + i }
    }

    fun part2(input: List<String>): Int {
        val pairs = input
            .map { it.split("   ") }
            .map { Pair(it[0], it[1]) }

        val left = pairs.map { it.first.toInt() }
        val right = pairs.map { it.second.toInt() }

        return left.map { computeSimilarity(it, right) }.fold(0) { acc, i -> acc + i }
    }

    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

fun computeSimilarity(target: Int, input: List<Int>): Int {
    return target * input.filter { it == target }.size
}
