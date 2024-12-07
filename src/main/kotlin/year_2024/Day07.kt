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
        for ((target, numbers) in input) {
            if (results(
                    operations = listOf(
                        { a, b -> a + b },
                        { a, b -> a * b },
                    ), numbers = numbers
                ).any { it == target }
            ) result += target
        }
        return result.toString()
    }

    override fun partTwo(): String {

        var result = BigInteger.ZERO
        for ((target, numbers) in input) {
            if (results(
                    operations = listOf(
                        { a, b -> a + b },
                        { a, b -> a * b },
                        { a, b -> (a.toString() + b.toString()).toBigInteger() }
                    ), numbers = numbers
                ).any { it == target }
            ) result += target
        }
        return result.toString()
    }

    private fun results(
        operations: List<(BigInteger, BigInteger) -> BigInteger>,
        results: List<BigInteger> = listOf(),
        numbers: List<BigInteger>
    ): List<BigInteger> {
        if (numbers.isEmpty()) return results
        if (results.isEmpty()) return results(operations, listOf(numbers.first()), numbers.drop(1))
        val number = numbers.first()
        val newResults = results.flatMap { result -> operations.map { operation -> operation(result, number) } }
        return results(operations, newResults, numbers.drop(1))
    }


}

