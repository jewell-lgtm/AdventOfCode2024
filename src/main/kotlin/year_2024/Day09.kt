package year_2024

import Puzzle
import java.math.BigInteger


class Day09(rawInput: String) : Puzzle(rawInput) {
    override fun partOne(): String {
        var toggle = false
        val result = mutableListOf<Char>()
        val positionFreeSpace = mutableListOf<Int>()
        var fileId = 0L
        for (number in rawInput.trim().map { it.toString().toInt() }) {
            toggle = !toggle
            if (toggle) {
                for (i in 0 until number) {
                    result.add(fileId.toString()[0])
                }
                fileId++
            } else {
                for (i in 0 until number) {
                    positionFreeSpace.add(result.size)
                    result.add('.')
                }
            }
        }

//        while (result.contains('.')) {
//            val last = result.removeLast()
//            if (last == '.') continue
//            val index = result.indexOfFirst { it == '.' }
//            result[index] = last
//        }

        while (positionFreeSpace.isNotEmpty() && positionFreeSpace.first() < result.size) {
            val char = result.removeLast()
            if (char == '.') continue
            val position = positionFreeSpace.removeFirst()
            if (result[position] != '.') error("Something went wrong")
            result[position] = char
        }

        val sum = result.foldIndexed(BigInteger.ZERO) { index, acc, char ->
            acc + if (char == '.') BigInteger.ZERO else index.toBigInteger() * char.toString().toBigInteger()
        }

        return sum.toString()
    }

    override fun partTwo(): String {
        return "TODO"
    }
}
