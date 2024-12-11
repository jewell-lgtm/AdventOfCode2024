package year_2024

import Puzzle

class Day11(rawInput: String) : Puzzle(rawInput) {
  override fun partOne(): String {
    val stones = rawInput.trim().split(" ").map { it.toLong() }

    return stones.sumOf { solveStone(it, 25) }.toString()
  }

  override fun partTwo(): String {
    val stones = rawInput.trim().split(" ").map { it.toLong() }

    return stones.sumOf { solveStone(it, 75) }.toString()
  }

  private val memo = mutableMapOf<Pair<Int, Long>, Long>()

  private fun solveStone(stone: Long, depth: Int) =
    memo.getOrPut(depth to stone) { solvePart(stone, depth) }

  private fun solvePart(stone: Long, depth: Int): Long {
    if (depth == 0) return 1L
    if (stone == 0L) return solveStone(1L, depth - 1)
    val strVal = stone.toString()
    if (strVal.length % 2 == 0) {
      val lhs = strVal.substring(0, strVal.length / 2)
      val rhs = strVal.substring(strVal.length / 2)
      return solveStone(lhs.toLong(), depth - 1) + solveStone(rhs.toLong(), depth - 1)
    }
    return solveStone(stone * 2024L, depth - 1)
  }
}
