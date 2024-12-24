fun main() {
    fun part1(input: List<String>): Long {
        val code = input.map { it.toCharArray().toList() }.first()
        var fileSystem = mutableListOf<String>()
        var fileIdCounter = 0
        code.forEachIndexed {
                            index, s ->
            var pad = " "
            if (index % 2 == 0) {
                pad = fileIdCounter.toString()
                fileIdCounter++
            }
            for (i in 1..s.digitToInt()) {
                fileSystem.add(pad)
            }
        }
        while (fileSystem.contains(" ")) {
            val targetFile = fileSystem.last()
            val emptyIndex = fileSystem.indexOfFirst { it == " " }
            fileSystem[emptyIndex] = targetFile
            fileSystem = fileSystem.dropLast(1).toMutableList()
        }
        return fileSystem.foldIndexed(0) {index, sum, s -> sum + (index * s.toInt())}
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day09_test")
    check(part1(testInput) == 1928L)
//    check(part2(testInput) == 1)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
