package year_2024

import Puzzle

class Day02(rawInput: String) : Puzzle(rawInput) {
    override fun partOne(): String {
        val reports = lines().map { it.toIntList() }
        return reports.count { it.isSafe() }.toString()
    }

    override fun partTwo(): String {
        val reports = lines().map { it.toIntList() }
        return reports.count { report -> report.permutations().any { it.isSafe() } }.toString()
    }

    private fun <E> List<E>.permutations() = sequence {
        yield(this@permutations)
        yieldAll(indices.asSequence().map { copyWithoutIndex(it) })
    }

    private fun <E> List<E>.copyWithoutIndex(index: Int): List<E> {
        if (index < 0) return emptyList()
        val newList = toMutableList()
        newList.removeAt(index)
        return newList
    }


    private fun String.toIntList() = split(" ").map { it.toInt() }

    private val negativeLevels = setOf(-3, -2, -1)
    private val positiveLevels = setOf(1, 2, 3)

    private fun List<Int>.isSafe(): Boolean {
        val allowedLevels = (positiveLevels + negativeLevels).toMutableSet()

        return zipWithNext().all { (first, second) ->
            val diff = first - second
            if (diff !in allowedLevels) return false
            if (diff in positiveLevels) allowedLevels.removeAll(negativeLevels)
            if (diff in negativeLevels) allowedLevels.removeAll(positiveLevels)
            true
        }
    }
}

