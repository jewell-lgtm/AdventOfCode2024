package year_2024

import Puzzle
import java.math.BigInteger

class Day07(rawInput: String) : Puzzle(rawInput) {
    private val input =
        lines().map { line ->
            line.split(": ")
                .let { linePart -> linePart[0].toBigInteger() to linePart[1].split(" ").map { it.toBigInteger() } }
        }

    override fun partOne(): String {
        var result = BigInteger.ZERO
        val operations = listOf<(BigInteger, BigInteger) -> BigInteger>(
            { a, b -> a + b },
            { a, b -> a * b },
        )
        for ((target, numbers) in input) {
            if (results(
                    operations, target, numbers
                ).any { it == target }
            ) result += target
        }
        return result.toString()
    }

    override fun partTwo(): String {
        var result = BigInteger.ZERO
        val operations = listOf<(BigInteger, BigInteger) -> BigInteger>(
            { a, b -> a + b },
            { a, b -> a * b },
            { a, b -> (a.toString() + b.toString()).toBigInteger() }
        )
        for ((target, numbers) in input) {
            if (results(
                    operations, target, numbers
                ).any { it == target }
            ) result += target
        }
        return result.toString()
    }

    private fun results(
        operations: List<(BigInteger, BigInteger) -> BigInteger>,
        target: BigInteger,
        numbers: List<BigInteger>,
        results: List<BigInteger> = listOf()
    ): List<BigInteger> {
        if (numbers.isEmpty()) return results
        if (results.isEmpty()) return results(operations, target, numbers.drop(1), listOf(numbers.first()))

        val number = numbers.first()
        val newResults = results.flatMap { result ->
            operations.mapNotNull { operation ->
                operation(
                    result,
                    number
                ).takeIf { it <= target }
            }
        }

        return results(operations, target, numbers.drop(1), newResults)
    }


}

