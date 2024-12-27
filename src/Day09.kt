import java.util.Optional

fun main() {
    fun part1(input: List<String>): Long {
        val code = input.map { it.toCharArray().toList() }.first()
        var fileSystem = mutableListOf<String>()
        var fileIdCounter = 0
        code.forEachIndexed { index, s ->
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
        return fileSystem.foldIndexed(0) { index, sum, s -> sum + (index * s.toInt()) }
    }

    fun part2(input: List<String>): Long {
        val code = input.map { it.toCharArray().toList() }.first()
        var fileSystem = mutableListOf<String>()
        var fileIdCounter = 0
        code.forEachIndexed { index, s ->
            var pad = " "
            if (index % 2 == 0) {
                pad = fileIdCounter.toString()
                fileIdCounter++
            }
            for (i in 1..s.digitToInt()) {
                fileSystem.add(pad)
            }
        }
        var target = fileSystem.last { it != " " }
        while (target.toInt() > 0) {
            val left = fileSystem.indexOfFirst { it == target }
            val right = fileSystem.indexOfLast { it == target }
            val len = right - left + 1

            val emptySlot = findEmptySlot(len, left, fileSystem)
            if (emptySlot.isPresent) {
                for (i in emptySlot.get()..(emptySlot.get() + len - 1)) {
                    fileSystem[i] = target
                }
                for (i in left..right) {
                    fileSystem[i] = " "
                }
            }
            target = target.toInt().minus(1).toString()
        }
        val checksum: Long = fileSystem
            .map { if (it == " ") "0" else it }
            .foldIndexed(0) { index, sum, s -> sum + (index * s.toInt()) }
        return checksum
    }

    val testInput = readInput("Day09_test")
    check(part1(testInput) == 1928L)
    check(part2(testInput) == 2858L)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}

fun findEmptySlot(targetLength: Int, currentPosition: Int, fileSystem: MutableList<String>): Optional<Int> {
    var lenCounter = 0
    for (i in 0 until currentPosition) {
        if (fileSystem[i] == " ") {
            lenCounter++
            if (lenCounter == targetLength) {
                return Optional.of(i - targetLength + 1)
            }
        } else {
            lenCounter = 0
        }
    }
    return Optional.empty<Int>()
}
