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
            .filter { isCreasingTolerant(it) }
            .filter { isGradualTolerant(it) }
            .size
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
//    check(part2(testInput) == 4)

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

fun isCreasingTolerant(report: List<Int>): Boolean {
    var valid = false

    valid = isCreasing(report)

    if (valid) {
        return true
    }

    for (i in 0..report.size - 1) {
        valid = isCreasing(report.filterIndexed { index, _ -> index != i })
        if (valid) {
            return true
        }
    }
    return false
}

//fun isCreasingTolerant(report: List<Int>): Boolean {
//    val increasing = report[0] < report[1]
//    var result = false
//    val creasingCheck = if (increasing) {
//        before: Int, current: Int -> before < current
//    } else {
//        before: Int, current: Int -> before > current
//    }
//
//    for (i in 1..report.size - 1) {
//        result = creasingCheck(report[i - 1], report[i])
//        if (!result) {
//            result = isCreasing(report.filterIndexed { index, _ -> index != i - 1 }) || isCreasing(report.filterIndexed { index, _ -> index != i })
//            break
//        }
//    }
//    return result
//}

fun isGradualTolerant(report: List<Int>): Boolean {
    var valid = false

    valid = isGradual(report)

    if (valid) {
        return true
    }

    for (i in 0..report.size - 1) {
        valid = isGradual(report.filterIndexed { index, _ -> index != i })
        if (valid) {
            return true
        }
    }
    return false
}

//fun isGradualTolerant(report: List<Int>): Boolean {
//    val diffs = report.subList(1, report.size)
//        .mapIndexed { index, i -> abs(report[index] - i) }
//    var valid = false
//    for (i in diffs.indices) {
//        valid = diffs[i] >= 1 && diffs[i] <= 3
//        if (!valid) {
//            valid = isGradual(report.filterIndexed { index, _ -> index != i - 1 }) || isGradual(report.filterIndexed { index, _ -> index != i })
//            break
//        }
//    }
//    return valid
//}

fun isGradual(report: List<Int>): Boolean {
    return report.subList(1, report.size)
        .mapIndexed { index, i -> abs(report[index] - i) }
        .all { it >= 1 && it <= 3 }
}