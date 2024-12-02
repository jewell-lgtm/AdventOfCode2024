package year_2024

import Puzzle
import kotlin.math.abs

class Day01(rawInput: String) : Puzzle(rawInput) {
    override fun partOne(): String {
        val pairs = cols().sorted().zipped()
        return pairs.pairwiseSum().toString()
    }


    override fun partTwo(): String {
        val (numbers, counts) = cols().run { first to second.toCounts() }
        return numbers.sumOf { counts.similarityScore(it) }.toString()
    }

    private fun cols() = lines().map { it.toIntPair() }.unzip()

    private fun Pair<List<Int>, List<Int>>.sorted() =
        first.sorted() to second.sorted()

    private fun Pair<List<Int>, List<Int>>.zipped() = first.zip(second)

    private fun List<Pair<Int, Int>>.pairwiseSum() = sumOf { (a, b) -> abs(a - b) }

    private fun List<Int>.toCounts() = groupingBy { it }.eachCount()

    private fun Map<Int, Int>.similarityScore(value: Int) = value * getOrDefault(value, 0)

    private fun String.toIntPair() = toInts().run { first() to last() }

    private fun String.toInts() = splitOnWhitespace().map { it.toInt() }

    private fun String.splitOnWhitespace() = split("""\s+""".toRegex())

}




