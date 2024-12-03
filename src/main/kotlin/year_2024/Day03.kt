package year_2024

import Puzzle

class Day03(rawInput: String) : Puzzle(rawInput) {
    override fun partOne(): String {
        val regex = """mul\((\d+),(\d+)\)""".toRegex()
        var product = 0
        for (result in regex.findAll(rawInput)) {
            val lhs = result.groupValues[1].toInt()
            val rhs = result.groupValues[2].toInt()
            product += lhs * rhs
        }
        return product.toString()
    }

    override fun partTwo(): String {
        val regex = """do\(\)|don't\(\)|(mul\((\d+),(\d+)\))""".toRegex()
        var product = 0
        var doInstruction = true
        for (result in regex.findAll(rawInput)) {
            if (result.groupValues[0] == "do()") doInstruction = true
            else if (result.groupValues[0] == "don't()") doInstruction = false
            else if (doInstruction) {
                val lhs = result.groupValues[2].toInt()
                val rhs = result.groupValues[3].toInt()
                product += lhs * rhs
            }
        }
        return product.toString()
    }
}
