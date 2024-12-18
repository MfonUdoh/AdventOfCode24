import java.util.Optional

fun main() {
    fun part1(input: List<String>): Int {
        val matrix: List<List<Char>> = input.map { it.toCharArray().toList() }
        val guardPosition = findGuard(matrix)
        val obstaclesPositions = findObstacles(matrix)
        val visited = mutableSetOf<Pair<Int, Int>>()
        var currentPosition = guardPosition
        var currentDirection = Direction.UP
        do {
            visited.add(currentPosition)
            val newState = moveGuard(currentPosition, obstaclesPositions, currentDirection, matrix.size)
            if (newState.isPresent) {
                currentDirection = newState.get().first
                currentPosition = newState.get().second
            }
        } while (newState.isPresent)
        return visited.size
    }

    fun lookAhead(
        currentPosition: Pair<Int, Int>,
        currentDirection: Direction,
        obstacles: List<Pair<Int, Int>>,
        matrixSize: Int,
        visited: MutableSet<Pair<Direction, Pair<Int, Int>>>
    ): Optional<Pair<Int, Int>> {
        var result = Optional.empty<Pair<Int, Int>>()
        val newObstacle = currentPosition.translatePosition(currentDirection)
        if (visited.any{ it.second == newObstacle}) return result
        val newObstacles = obstacles.toMutableList()
        newObstacles.add(newObstacle)

        var currentPosition = currentPosition
        var currentDirection = currentDirection
        val lookAheadVisited = visited.toMutableSet()

        do {
            lookAheadVisited.add(Pair(currentDirection, currentPosition))
            val newState = moveGuard(currentPosition, newObstacles, currentDirection, matrixSize)
            if (newState.isPresent) {
                if (lookAheadVisited.contains(newState.get())) {
                    result = Optional.of(newObstacle)
                    break
                }
                currentDirection = newState.get().first
                currentPosition = newState.get().second
            }
        } while (newState.isPresent)
        return result
    }

    fun part2(input: List<String>): Int {
        val matrix: List<List<Char>> = input.map { it.toCharArray().toList() }
        val guardPosition = findGuard(matrix)
        val obstaclesPositions = findObstacles(matrix)
        val visited = mutableSetOf<Pair<Direction, Pair<Int, Int>>>()
        val obstacleLoopPositions = mutableSetOf<Pair<Int, Int>>()
        var currentPosition = guardPosition
        var currentDirection = Direction.UP
        do {
            val obstacleLoop = lookAhead(currentPosition, currentDirection, obstaclesPositions, matrix.size, visited)
            if (obstacleLoop.isPresent) {
                obstacleLoopPositions.add(obstacleLoop.get())
            }
            visited.add(Pair(currentDirection, currentPosition))
            val newState = moveGuard(currentPosition, obstaclesPositions, currentDirection, matrix.size)
            if (newState.isPresent) {
                currentDirection = newState.get().first
                currentPosition = newState.get().second
            }
        } while (newState.isPresent)
        return obstacleLoopPositions.size
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 41)
    check(part2(testInput) == 6)

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
    obstaclesPositions: List<Pair<Int, Int>>,
    direction: Direction,
    matrixSize: Int
): Optional<Pair<Direction, Pair<Int, Int>>> {
    val targetPosition = guardPosition.translatePosition(direction)
    if (matrixSize <= targetPosition.first
        || targetPosition.first < 0
        || matrixSize <= targetPosition.second
        || targetPosition.second < 0
    ) {
        return Optional.empty()
    }

    if (obstaclesPositions.any { it == targetPosition }) {
        return Optional.of(Pair(direction.turn(), guardPosition))
    }

    return Optional.of(Pair(direction, targetPosition))
}

private fun Pair<Int, Int>.translatePosition(direction: Direction): Pair<Int, Int> {
    return when (direction) {
        Direction.UP -> {
            Pair(this.first, this.second - 1)
        }

        Direction.DOWN -> {
            Pair(this.first, this.second + 1)
        }

        Direction.LEFT -> {
            Pair(this.first - 1, this.second)
        }

        Direction.RIGHT -> {
            Pair(this.first + 1, this.second)
        }
    }
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