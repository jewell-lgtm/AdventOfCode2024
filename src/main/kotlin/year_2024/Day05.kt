package year_2024

import Puzzle

class Day05(rawInput: String) : Puzzle(rawInput) {
    override fun partOne(): String {
        val (mustAppearBefore, mustAppearAfter, pageNumbers) = rawInput.split("\n\n").map { it.trim() }
            .let { list ->
                Triple(toBeforeRules(list[0]), toAfterRules(list[0]), toPageNumbers(list[1]))
            }

        var result = 0
        for (numbers in pageNumbers) {
            val isOkay = numbers.filterIndexed() { index, number ->
                isNumberViolation(
                    number,
                    numbers.subList(0, index),
                    numbers.subList(index + 1, numbers.size),
                    mustAppearBefore,
                    mustAppearAfter
                )
            }.isEmpty()
            if (isOkay) result += numbers.getAtMiddle()
        }

        return result.toString()
    }

    private fun anyNumberViolation(
        numbers: List<Int>,
        mustAppearBefore: Map<Int, List<Int>>,
        mustAppearAfter: Map<Int, List<Int>>
    ): Boolean {
        numbers.forEachIndexed { index, number ->
            if (isNumberViolation(
                    number,
                    numbers.subList(0, index),
                    numbers.subList(index + 1, numbers.size),
                    mustAppearBefore,
                    mustAppearAfter
                )
            ) return true
        }
        return false
    }

    private fun isNumberViolation(
        number: Int,
        before: List<Int>,
        after: List<Int>,
        rulesBefore: Map<Int, List<Int>>,
        rulesAfter: Map<Int, List<Int>>
    ): Boolean {
        for (rule in rulesBefore[number] ?: emptyList()) {
            if (after.contains(rule)) return true
        }
        for (rule in rulesAfter[number] ?: emptyList()) {
            if (before.contains(rule)) return true
        }
        return false
    }


    override fun partTwo(): String {
        val (mustAppearBefore, mustAppearAfter, pageNumbers) = rawInput.split("\n\n").map { it.trim() }
            .let { list ->
                Triple(toBeforeRules(list[0]), toAfterRules(list[0]), toPageNumbers(list[1]))
            }

        var result = 0

        for (numbersList in pageNumbers.map { it.toMutableList() }) {
            if (!anyNumberViolation(
                    numbersList,
                    mustAppearBefore,
                    mustAppearAfter
                )
            ) continue

            // cursed sorting algo
            var i = 0
            while (i < numbersList.size) {
                val sublistBefore = numbersList.subList(0, i).toSet()
                val mustAppearAfterNumber = mustAppearAfter[numbersList[i]].orEmpty().toSet()
                val violations = sublistBefore.intersect(mustAppearAfterNumber)
                if (violations.isNotEmpty()) {
                    numbersList.swapAtIndices(i, i - 1)
                    i--
                } else {
                    i++
                }
            }


            val middleIndex = numbersList.getAtMiddle()
            result += middleIndex
        }


        return result.toString()
    }
}

private fun <E> MutableList<E>.swapAtIndices(i: Int, i2: Int) {
    val temp = this[i2]
    this[i2] = this[i]
    this[i] = temp
}

private fun <E> List<E>.getAtMiddle(): E {
    val middle = this.size.floorDiv(2)
    return this[middle]
}

private fun toBeforeRules(input: String) = input.split("\n")
    .map { line -> line.split("|") }
    .groupBy({ it[1].toInt() }, { it[0].toInt() })

private fun toAfterRules(input: String) = input.split("\n")
    .map { line -> line.split("|") }
    .groupBy({ it[0].toInt() }, { it[1].toInt() })

private fun toPageNumbers(input: String) = input.split("\n")
    .map { line -> line.split(",").map { it.toInt() } }
