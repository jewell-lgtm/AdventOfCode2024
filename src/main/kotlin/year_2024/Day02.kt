package year_2024

import Puzzle

class Day02(rawInput: String) : Puzzle(rawInput) {
    override fun partOne(): String {
        return reports().count { it.isSafe() }.toString()
    }

    override fun partTwo(): String {
        return reports().count { report -> report.permutations().any { it.isSafe() } }.toString()
    }

    private fun reports() = lines().map { it.toIntList() }

    private fun String.toIntList() = split(" ").map { it.toInt() }

    private fun <E> List<E>.permutations() =
        indices.asSequence().map { index -> until(index) + after(index) }

    private fun <E> List<E>.after(index: Int) = slice(index + 1 until size)

    private fun <E> List<E>.until(index: Int) = slice(0 until index)

    private fun List<Int>.isSafe(): Boolean = diffs()
        .all { it in 1..3 } || diffs().all { it in -3..-1 }

    private fun List<Int>.diffs() = zipWithNext().map { (first, second) -> first - second }
}

