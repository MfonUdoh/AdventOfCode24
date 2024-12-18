import java.util.Optional

fun main() {
    fun part1(input: List<String>): Int {
        val matrix: List<List<Char>> = input.map { it.toCharArray().toList() }
        val guardPosition = findGuard(matrix)
        val obstaclesPositions = findObstacles(matrix)
        val visited = mutableSetOf<Pair<Int,Int>>()
        var currentPosition = guardPosition
        var currentDirection = Direction.UP
        do {
            visited.add(currentPosition)
            val newState = moveGuard(currentPosition, obstaclesPositions, currentDirection, matrix)
            if (newState.isPresent) {
                currentDirection = newState.get().first
                currentPosition = newState.get().second
            }
        } while (newState.isPresent)
        return visited.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 41)
//    check(part2(testInput) == 1)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}

fun findGuard(matrix: List<List<Char>>): Pair<Int, Int> {
    for (y in matrix.indices) {
        for (x in matrix[y].indices) {
            if (matrix[y][x] == '^') {
                return Pair(x, y)
            }
        }
    }
    return Pair(0, 0)
}

fun moveGuard(
    guardPosition: Pair<Int, Int>,
    obstaclesPostions: List<Pair<Int, Int>>,
    direction: Direction,
    matrix: List<List<Char>>
): Optional<Pair<Direction,Pair<Int, Int>>> {
    val transform = when (direction) {
        Direction.UP -> {pair: Pair<Int, Int> -> Pair(pair.first, pair.second-1)}
        Direction.DOWN -> {pair: Pair<Int, Int> -> Pair(pair.first,pair.second+1)}
        Direction.LEFT -> {pair: Pair<Int, Int> -> Pair(pair.first-1,pair.second)}
        Direction.RIGHT -> {pair: Pair<Int, Int> -> Pair(pair.first+1,pair.second)}
    }

    val targetPosition = transform(guardPosition)
    if (matrix[0].size <= targetPosition.first
        || targetPosition.first < 0
        || matrix.size <= targetPosition.second
        || targetPosition.second < 0) {
        return Optional.empty()
    }

    if (obstaclesPostions.any { it == targetPosition }) {
        return Optional.of(Pair(direction.turn(), guardPosition))
    }

    return Optional.of(Pair(direction, targetPosition))
}

fun findObstacles(matrix: List<List<Char>>): List<Pair<Int, Int>> {
    val obstacles = mutableListOf<Pair<Int, Int>>()
    for (y in matrix.indices) {
        for (x in matrix[y].indices) {
            if (matrix[y][x] == '#') obstacles.add(Pair(x, y))
        }
    }
    return obstacles
}

enum class Direction {
    UP, DOWN, LEFT, RIGHT;

    fun turn(): Direction {
        return when (this) {
            UP -> RIGHT
            RIGHT -> DOWN
            DOWN -> LEFT
            LEFT -> UP
        }
    }
}