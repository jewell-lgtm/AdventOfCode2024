package year_2024

import Puzzle

class Day01(rawInput: String) : Puzzle(rawInput) {
    override fun partOne(): String {
        val (first, second) = toIntLists(lines())
        var diff = 0
        for (i in first.indices) {
            diff += Math.abs(first[i] - second[i])
        }
        return diff.toString()
    }

    override fun partTwo(): String {
        val (first, second) = toIntLists(lines())
        val secondFreq = second.groupingBy { it }.eachCount()
        var score = 0
        for (i in first) {
            score += i * (secondFreq[i] ?: 0)
        }
        return score.toString()
    }

    fun toIntLists(lines: List<String>): Pair<List<Int>, List<Int>> {
        val first = mutableListOf<Int>()
        val second = mutableListOf<Int>()
        lines.forEach { line ->
            val (firstStr, secondStr) = line.split("\\s+".toRegex())
            first.add(firstStr.toInt())
            second.add(secondStr.toInt())
        }
        return first.sorted() to second.sorted()
    }
}
