package year_2024

import Puzzle

class Day06(rawInput: String) : Puzzle(rawInput) {
    val grid = lines().map { it.toList() }

    override fun partOne(): String {
        val guard = grid.scan('^')?.toGuard(Direction.N) ?: error("No guard found")
        val seen = mutableSetOf<Position>()
        while (true) {
            seen.add(guard.pos)
            val next = guard.pos + guard.dir
            if (isOutOfBounds(next)) break
            if (grid[next] == '#') guard.dir = guard.dir.rotate()
            else guard.pos = next
        }
        return seen.size.toString()
    }

    override fun partTwo(): String {
        var cycles = 0
        for (grid in allGrids()) {
            val guard = grid.scan('^')?.toGuard(Direction.N) ?: error("No guard found")
            val seen = mutableSetOf<Pair<Position, Direction>>()
            while (true) {
                seen.add(guard.pos to guard.dir)
                val next = guard.pos + guard.dir
                if (isOutOfBounds(next)) break
                if (grid[next] == '#') guard.dir = guard.dir.rotate()
                else guard.pos = next

                if (seen.contains(guard.pos to guard.dir)) {
                    cycles++
                    break
                }
            }
        }
        return cycles.toString()
    }

    private fun allGrids() = sequence {
        for (r in 0 until rows) {
            for (c in 0 until columns) {
                if (grid[r][c] == '.') yield(grid.map { it.toMutableList() }.apply { this[r][c] = '#' })
            }
        }
    }


    private fun List<List<Char>>.debug(): String {
        return this.joinToString("\n") { row -> row.joinToString("") }
    }

    private val rows = lines().size
    private val columns = lines().first().length

    private fun isOutOfBounds(pos: Position) = pos.r < 0 || pos.r >= rows || pos.c < 0 || pos.c >= columns

    private enum class Direction(val dr: Int, val dc: Int) {
        N(-1, 0), E(0, 1), S(1, 0), W(0, -1);
    }

    private data class Position(val r: Int, val c: Int)
    private class Guard(var pos: Position, var dir: Direction)

    // matrices are hard
    private fun Direction.rotate() =
        when (this) {
            Direction.N -> Direction.E
            Direction.E -> Direction.S
            Direction.S -> Direction.W
            Direction.W -> Direction.N
        }

    private fun Position.toGuard(dir: Direction) = Guard(this, dir)

    private fun List<List<Char>>.scan(char: Char) = positions().firstOrNull { this[it] == char }
    private operator fun List<List<Char>>.get(at: Position) = this[at.r][at.c]
    private operator fun List<MutableList<Char>>.set(at: Position, char: Char) {
        this[at.r][at.c] = char
    }

    private fun List<List<Char>>.positions() = sequence {
        for (r in 0 until rows) {
            for (c in 0 until columns) {
                yield(Position(r, c))
            }
        }
    }

    private operator fun Position.plus(dir: Direction) = Position(r + dir.dr, c + dir.dc)
}




