import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map { it.split(" ").map { it.toInt() } }
            .filter { isCreasing(it) }
            .filter { isGradual(it) }
            .size
    }

    fun part2(input: List<String>): Int {
        return input
            .map { it.split(" ").map { it.toInt() } }
            .filter { isCreasingAndGradualTolerant(it) }
            .size
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

fun isCreasing(report: List<Int>): Boolean {
//    if (report[0] == report[1]) { return false }
    val increasing = report[0] < report[1]
    if (increasing) {
        return report == report.sortedBy { it }
    } else {
        return report == report.sortedByDescending { it }
    }
}

fun isGradual(report: List<Int>): Boolean {
    return report.subList(1, report.size)
        .mapIndexed { index, i -> abs(report[index] - i) }
        .all { it >= 1 && it <= 3 }
}

fun isCreasingAndGradualTolerant(report: List<Int>): Boolean {
    var valid = false

    valid = isCreasing(report) && isGradual(report)

    if (valid) {
        return true
    }

    for (i in 0..report.size - 1) {
        valid = isCreasing(report.filterIndexed { index, _ -> index != i }) && isGradual(report.filterIndexed { index, _ -> index != i })
        if (valid) {
            return true
        }
    }
    return false
}
