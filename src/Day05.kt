fun main() {
    fun part1(input: List<String>): Int {
        var isInstruction = false
        var score = 0
        val ruleList = mutableListOf<Pair<Int, Int>>()
        for (i in input.indices) {
            val curr = input[i]
            if (curr == "") {
                isInstruction = true
                continue
            }

            if (isInstruction) {
                val instructions = curr.split(",").map { it.toInt() }
                val valid = checkInstructions(instructions, ruleList)
                if (valid) {
                    score += getMiddleValue(instructions)
                }
            } else {
                val rule = addRule(curr)
                ruleList.add(rule)
            }
        }
        return score
    }

    fun part2(input: List<String>): Int {
        var isInstruction = false
        var score = 0
        val ruleList = mutableListOf<Pair<Int, Int>>()
        for (i in input.indices) {
            val curr = input[i]
            if (curr == "") {
                isInstruction = true
                continue
            }

            if (isInstruction) {
                val instructions = curr.split(",").map { it.toInt() }
                val valid = checkInstructions(instructions, ruleList)
                if (!valid) {
                    score += getMiddleValue2(instructions, ruleList)
                }
            } else {
                val rule = addRule(curr)
                ruleList.add(rule)
            }
        }
        return score
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 143)
    check(part2(testInput) == 123)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}

fun addRule(string: String): Pair<Int, Int> {
    val couple = string.split("|").map { it.toInt() }
    return Pair(couple[0], couple[1])
}

fun checkInstructions(instructions: List<Int>, pairs: List<Pair<Int, Int>>): Boolean {
    for (i in instructions.indices) {
        val check = pairs
            .filter { it.first == instructions[i] }
            .filter { instructions.contains(it.second) }
            .all { instructions.indexOf(it.second) > i }
        if (!check) return false
    }
    return true
}

fun getMiddleValue(instructions: List<Int>): Int {
    val mid = (instructions.size - 1) / 2
    return instructions[mid]
}

fun getMiddleValue2(instructions: List<Int>, pairs: List<Pair<Int, Int>>): Int {
    var resultSet = instructions.toMutableList()
    do {
        val brokenRules = pairs
            .filter {
                resultSet.contains(it.first)
                        && resultSet.contains(it.second)
                        && resultSet.indexOf(it.second) < resultSet.indexOf(it.first)
            }
        if (brokenRules.isNotEmpty()) {
            val rule = brokenRules.first()
            val left = resultSet.indexOf(rule.first)
            val right = resultSet.indexOf(rule.second)
            resultSet[left] = rule.second
            resultSet[right] = rule.first
        }
    } while (brokenRules.isNotEmpty())

    return getMiddleValue(resultSet)
}
