fun main() {
    fun part1(input: List<String>): Long {
        val inputSplit = input.map { it.split(":") }
        var score:Long = 0
        for (i in inputSplit.indices) {
            val split = inputSplit[i]
            val target = split[0].toLong()
            val equation = split[1].trim().split(" ").map { it.toLong() }

            val valid = validateEquation(target, equation)
            if (valid) {
                score += target
            }
        }
        return score
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 3749L)
//    check(part2(testInput) == 1)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}

fun validateEquation(target: Long, equation: List<Long>): Boolean {
    return applyOperator(equation.get(0), equation.subList(1, equation.size), Operation.ADD, target)
            || applyOperator(equation.get(0), equation.subList(1, equation.size), Operation.MULTIPLY, target)
}

fun applyOperator(sum: Long, remainingEquation: List<Long>, operation: Operation, target: Long): Boolean {
    if (remainingEquation.isEmpty()) {
        return sum == target
    }
    val newSum = operation.apply(sum, remainingEquation.get(0))
    return applyOperator(newSum, remainingEquation.subList(1, remainingEquation.size), Operation.ADD, target)
            || applyOperator(newSum, remainingEquation.subList(1, remainingEquation.size), Operation.MULTIPLY, target)
}

enum class Operation {

    ADD, MULTIPLY;

    fun apply(a: Long, b: Long): Long {
        return when (this) {
            ADD -> a + b
            MULTIPLY -> a * b
        }
    }
}
