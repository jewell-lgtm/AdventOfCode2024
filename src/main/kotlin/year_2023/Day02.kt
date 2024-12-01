package year_2023

import Puzzle

class Day02(rawInput: String) : Puzzle(rawInput) {
    override fun partOne(): String {
        val games = lines().map { it.toGame() }
        val possibleGames = games.filter { it.maxRed <= 12 && it.maxGreen <= 13 && it.maxBlue <= 14 }
        return "" + possibleGames.sumOf { it.id }
    }

    override fun partTwo(): String {
        return "1"
    }


    data class Game(val id: Int, val maxBlue: Int, val maxRed: Int, val maxGreen: Int)

    fun String.toGame(): Game {
        val (first, second) = split(": ")
        val gameId = first.split(" ")[1].toInt()
        val gameDetails = second.split("; ")

        val maxBlue = gameDetails.maxOf { str ->
            val result = Regex("(\\d+) blue").find(str)
            result?.groupValues?.get(1)?.toInt() ?: 0
        }
        val maxRed = gameDetails.maxOf { str ->
            val result = Regex("(\\d+) red").find(str)
            result?.groupValues?.get(1)?.toInt() ?: 0
        }
        val maxGreen = gameDetails.maxOf { str ->
            val result = Regex("(\\d+) green").find(str)
            result?.groupValues?.get(1)?.toInt() ?: 0
        }
        return Game(gameId, maxBlue, maxRed, maxGreen)
    }
}
