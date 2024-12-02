package year_2024

import Puzzle
import kotlin.math.abs

class Day01(rawInput: String) : Puzzle(rawInput) {
    override fun partOne(): String {
        val (first, second) = lines().toIntLists()
        val diff = first.indices.sumOf { i -> abs(first[i] - second[i]) }
        return diff.toString()
    }

    override fun partTwo(): String {
        val (first, second) = lines().toIntLists()
        val secondFreq = second.groupingBy { it }.eachCount()
        val score = first.sumOf { firstInt -> firstInt * (secondFreq.getOrDefault(firstInt, 0)) }
        return score.toString()
    }

    private fun List<String>.toIntLists(): Pair<List<Int>, List<Int>> =
        fold(mutableListOf<Int>() to mutableListOf<Int>()) { (firstList, secondList), line ->
            val (first, second) = line.split("\\s+".toRegex()).map { it.toInt() }
            firstList.add(first)
            secondList.add(second)
            firstList to secondList
        }.let { it.first.sorted() to it.second.sorted() }
}
