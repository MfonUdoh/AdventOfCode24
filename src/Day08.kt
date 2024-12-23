fun main() {

    fun getAntinodes(antennas: MutableList<Pair<Int, Int>>, matrixSize: Int): MutableList<Pair<Int, Int>> {
        val antinodes = mutableListOf<Pair<Int, Int>>()
        for (i in antennas.indices) {
            val nodes = antennas
                .filterIndexed { index, _ -> index != i }
                .map { it.translate(antennas[i]) }
                .filter { it.validateCoordinate(matrixSize) }
            antinodes.addAll(nodes)
        }
        return antinodes
    }

    fun part1(input: List<String>): Int {
        val matrix = input.map { it.toCharArray().toList() }
        val antennas = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (matrix[i][j] != '.') {
                    val frequency = antennas.getOrDefault(matrix[i][j], mutableListOf())
                    frequency.add(Pair(i, j))
                    antennas.put(matrix[i][j], frequency)
                }
            }
        }

        val antinodes = antennas
            .map { getAntinodes(it.value, matrix.size) }.flatten().toSet()
        return antinodes.size
    }

    fun part2(input: List<String>): Int {
        val matrix = input.map { it.toCharArray().toList() }
        val antennas = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (matrix[i][j] != '.') {
                    val frequency = antennas.getOrDefault(matrix[i][j], mutableListOf())
                    frequency.add(Pair(i, j))
                    antennas.put(matrix[i][j], frequency)
                }
            }
        }

        val antinodes = antennas
            .map { getAntinodes2(it.value, matrix.size) }
            .flatten()
            .toSet()
        return antinodes.size
    }

    val testInput = readInput("Day08_test")
    check(part1(testInput) == 14)
    check(part2(testInput) == 34)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}

private fun Pair<Int, Int>.validateCoordinate(matrixSize: Int): Boolean {
    return this.first >= 0 && this.first < matrixSize && this.second >= 0 && this.second < matrixSize
}

private fun Pair<Int, Int>.translate(other: Pair<Int, Int>): Pair<Int, Int> {
    val diffY = other.first - this.first
    val diffX = other.second - this.second
    return Pair(other.first + diffY, other.second + diffX)
}

fun getAntinodes2(antennas: MutableList<Pair<Int, Int>>, matrixSize: Int): MutableList<Pair<Int, Int>> {
    val antinodes = mutableListOf<Pair<Int, Int>>()
    for (i in antennas.indices) {
        antennas
            .filterIndexed { index, _ -> index != i }
            .map { node -> antinodes.add(node); node }
            .map { getNodes(antennas[i], it, matrixSize, antinodes) }
    }
    return antinodes
}

fun getNodes(prevNode: Pair<Int, Int>, currentNode: Pair<Int, Int>, matrixSize: Int, container: MutableList<Pair<Int, Int>>): MutableList<Pair<Int, Int>> {
    val newNode = prevNode.translate(currentNode)
    if (newNode.validateCoordinate(matrixSize)) {
        container.add(newNode)
        return getNodes(currentNode, newNode, matrixSize, container)
    }
    return container
}
