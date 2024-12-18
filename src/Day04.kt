import kotlin.math.max
import kotlin.math.min

fun main() {

    fun part1(input: List<String>): Int {
        val matrix: List<List<Char>> = input.map { it.toCharArray().toList() }
        var sum = 0
        for (y in matrix.indices) {
            for (x in matrix[0].indices) {
                if (matrix[y][x] == 'X') {
                    if (checkRight(y, x, matrix)) sum ++
                    if (checkLeft(y, x, matrix)) sum ++
                    if (checkUp(y, x, matrix)) sum ++
                    if (checkDown(y, x, matrix)) sum ++
                    sum += checkDiagonals(y, x, matrix)
                }
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val matrix: List<List<Char>> = input.map { it.toCharArray().toList() }
        var sum = 0
        for (y in matrix.indices) {
            for (x in matrix[0].indices) {
                if (matrix[y][x] == 'A') {
                    if (checkMas(y, x, matrix)) sum ++
                }
            }
        }
        return sum
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}

fun checkRight(y: Int, x: Int, matrix: List<List<Char>>): Boolean {
    val end = min(x+3, matrix[0].size - 1)
    return getHorizontal(x, y, end, matrix).isXmas()
}

fun checkLeft(y: Int, x: Int, matrix: List<List<Char>>): Boolean {
    val end = max(x-3, 0)
    return getHorizontal(end, y, x, matrix).reversed().isXmas()
}

fun checkUp(y: Int, x: Int, matrix: List<List<Char>>): Boolean {
    val end = max(y-3, 0)
    return getVertical(x, end, y, matrix).reversed().isXmas()
}

fun checkDown(y: Int, x: Int, matrix: List<List<Char>>): Boolean {
    val end = min(y+3, matrix.size-1)
    return getVertical(x, y, end, matrix).isXmas()
}

fun checkDiagonals(y: Int, x: Int, matrix: List<List<Char>>): Int {
    var score = 0
    if (getDiagonal(x, y, true, true, 4, matrix).isXmas()) score++
    if (getDiagonal(x, y, true, false, 4, matrix).isXmas()) score++
    if (getDiagonal(x, y, false, true, 4, matrix).isXmas()) score++
    if (getDiagonal(x, y, false, false, 4, matrix).isXmas()) score++
    return score
}

fun checkMas(y: Int, x: Int, matrix: List<List<Char>>): Boolean {
    if (y >= 1 && y <= matrix.size - 2 && x >= 1 && x <= matrix[0].size - 2) {
        return getDiagonal(x-1, y-1, true, true, 3, matrix).isMas() && getDiagonal(x-1, y+1, true, false, 3, matrix).isMas()
    }
    return false
}

private fun List<Char>.isXmas(): Boolean {
    return this.joinToString("") == "XMAS"
}

private fun List<Char>.isMas(): Boolean {
    val str = this.joinToString("")
    return str == "MAS" || str == "SAM"
}

fun getHorizontal(startX: Int, startY: Int, endX: Int, matrix: List<List<Char>>): List<Char> {
    val result = mutableListOf<Char>()
    for (i in startX..endX) {
        result.add(matrix[startY][i])
    }
    return result
}

fun getVertical(startX: Int, startY: Int, endY: Int, matrix: List<List<Char>>): List<Char> {
    val result = mutableListOf<Char>()
    for (i in startY..endY) {
        result.add(matrix[i][startX])
    }
    return result
}

fun getDiagonal(startX: Int, startY: Int, incX: Boolean, incY: Boolean, len:Int, matrix: List<List<Char>>): List<Char> {
    val result = mutableListOf<Char>()
    var counter = 0
    var curX = startX
    var curY = startY
    val inBounds = {
        curX >= 0 && curY >= 0 && curX < matrix[0].size && curY < matrix.size
    }
    while (counter < len && inBounds()) {
        result.add(matrix[curY][curX])
        curX += if (incX) 1 else -1
        curY += if (incY) 1 else -1
        counter++
    }
    return result
}

