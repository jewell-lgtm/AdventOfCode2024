package year_2024

import Puzzle
import kotlin.math.abs

class Day01(rawInput: String) : Puzzle(rawInput) {
    override fun partOne(): String {
        return lines()
            .toIntListPair()
            .sorted()
            .zipped()
            .sumOf { (a, b) -> abs(a - b) }
            .toString()
    }


    override fun partTwo(): String {
        val (numbers, counts) = lines().toIntListPair().run { first to second.toCounts() }
        return numbers.sumOf { counts.similarityScore(it) }.toString()
    }

    private fun Pair<List<Int>, List<Int>>.sorted() =
        first.sorted() to second.sorted()

    private fun Pair<List<Int>, List<Int>>.zipped() = first.zip(second)

    private fun List<Int>.toCounts() = groupingBy { it }.eachCount()

    private fun Map<Int, Int>.similarityScore(value: Int) = value * getOrDefault(value, 0)

    private fun List<String>.toIntListPair() = map { it.toIntPair() }.unzip()

    private fun String.toIntPair() =
        splitOnWhitespace()
            .map { it.toInt() }
            .let { it[0] to it[1] }

    private fun String.splitOnWhitespace() = split("""\s+""".toRegex())

}




