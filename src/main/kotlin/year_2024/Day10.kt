package year_2024

import Puzzle

class Day10(rawInput: String) : Puzzle(rawInput) {

  override fun partOne(): String {
    val grid = cells().map { it.digitToIntOrNull() ?: -1 }
    var sum = 0
    for (start in grid.findAll(0)) {
      val found = mutableSetOf<Position>()
      val queue = mutableListOf(start)
      while (queue.isNotEmpty()) {
        val position = queue.removeFirst()
        if (grid[position] == 9) {
          found.add(position)
          continue
        }
        val next = position.neighbors().filter { neighbor -> grid[neighbor] == grid[position] + 1 }
        queue.addAll(next)
      }

      sum += found.size
    }

    return sum.toString()
  }

  override fun partTwo(): String {
    val grid = cells().map { it.digitToIntOrNull() ?: -1 }
    var sum = 0
    for (start in grid.findAll(0)) {
      val queue = mutableListOf(start)
      while (queue.isNotEmpty()) {
        val position = queue.removeFirst()
        if (grid[position] == 9) {
          sum++
          continue
        }
        val next = position.neighbors().filter { neighbor -> grid[neighbor] == grid[position] + 1 }
        queue.addAll(next)
      }
    }

    return sum.toString()
  }

  private val rows = lines().size
  private val columns = lines().first().length

  private data class Position(val r: Int, val c: Int)

  private enum class Direction(val dr: Int, val dc: Int) {
    Up(0, -1),
    Down(0, 1),
    Left(-1, 0),
    Right(1, 0)
  }

  private fun Position.isValid() = r in 0..<rows && c in 0..<columns

  private fun Position.neighbors() =
    Direction.entries.mapNotNull { dir ->
      Position(r + dir.dr, c + dir.dc).takeIf { it.isValid() }
    }

  private data class Grid2d<E>(val input: List<List<E>>)

  private operator fun <E> Grid2d<E>.get(pos: Position) = input[pos.r][pos.c]

  private fun cells(): Grid2d<Char> = Grid2d(lines().map { it.toList() })

  private fun <In, Out> Grid2d<In>.map(transform: (In) -> Out) =
    Grid2d(input.map { it.map(transform) })

  private fun <E> Grid2d<E>.findAll(value: E): Set<Position> =
    input
      .flatMapIndexed { r, row ->
        row.withIndex().flatMap { (c, it) ->
          if (it == value) setOf(Position(r, c)) else emptySet()
        }
      }
      .toSet()
}
