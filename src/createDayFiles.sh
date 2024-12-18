#!/bin/zsh

touch Day"$1".txt
touch Day"$1"_test.txt

echo "fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput(\"Day$1_test\")
    check(part1(testInput) == 1)
//    check(part2(testInput) == 1)

    val input = readInput(\"Day$1\")
    part1(input).println()
    part2(input).println()
}" > Day"$1".kt

git add Day"$1".kt