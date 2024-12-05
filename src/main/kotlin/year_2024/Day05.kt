package year_2024

import Puzzle

class Day05(rawInput: String) : Puzzle(rawInput) {
    override fun partOne(): String {
        val pageNumberLists = rawInput.split("\n\n")[1].trim().split("\n")
            .map { line -> line.split(",").map { it.toInt() } }

        var result = 0
        for (numbers in pageNumberLists) {
            if (areAllNumbersValid(numbers)) {
                val middle = numbers.getAtMiddle()
                result += middle
            }
        }

        return result.toString()
    }

    override fun partTwo(): String {
        val pageNumberLists = rawInput.split("\n\n")[1].trim().split("\n")
            .map { line -> line.split(",").map { it.toInt() } }


        var result = 0
        for (numbers in pageNumberLists.map { it.toMutableList() }) {
            if (areAllNumbersValid(numbers)) continue

            // cursed sorting algorithm
            var i = 0
            while (i < numbers.size) {
                val number = numbers[i]
                val numbersBefore = numbers.subList(0, i).toSet()
                val numbersMustAppearAfter = mustAppearAfter[number].orEmpty()
                val violations = numbersBefore.intersect(numbersMustAppearAfter)
                if (violations.isNotEmpty()) {
                    numbers.swapAtIndices(i, i - 1)
                    i--
                } else {
                    i++
                }
            }

            val middle = numbers.getAtMiddle()
            result += middle
        }

        return result.toString()
    }

    private val mustAppearAfter = rawInput.split("\n\n")[0].split("\n")
        .map { it.split("|") }
        .groupBy({ it[0].toInt() }, { it[1].toInt() })
        .mapValues { it.value.toSet() }

    private fun areAllNumbersValid(
        numbers: List<Int>
    ): Boolean {
        numbers.forEachIndexed { i, number ->
            val numbersBefore = numbers.subList(0, i).toSet()
            val numbersMustAppearAfter = mustAppearAfter[number].orEmpty()
            val violations = numbersBefore.intersect(numbersMustAppearAfter)
            if (violations.isNotEmpty()) {
                return false
            }
        }
        return true
    }

    private fun <E> List<E>.getAtMiddle(): E {
        val middle = this.size.floorDiv(2)
        return this[middle]
    }


    private fun <E> MutableList<E>.swapAtIndices(x: Int, y: Int) {
        val temp = this[x]
        this[x] = this[y]
        this[y] = temp
    }
}
