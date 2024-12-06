package year_2024

import Puzzle

class Day06(rawInput: String) : Puzzle(rawInput) {
    private val grid = lines().map { it.toList() }

    override fun partOne(): String {
        val guard = grid.scan('^')?.toGuard(Direction.N) ?: error("No guard found")
        return traverse(grid, guard).seen.toString()
    }

    override fun partTwo(): String {
        val timeStarted = System.currentTimeMillis()
        val guard = grid.scan('^')?.toGuard(Direction.N) ?: error("No guard found")
        return allGrids().count { traverse(it, guard.copy()).isCycle }
            .toString()
            .also {
                println("Took ${System.currentTimeMillis() - timeStarted}ms")
            }
    }

    private data class TraverseResult(
        val seen: Int,
        val isCycle: Boolean,
    )

    private fun traverse(
        grid: List<List<Char>>,
        guard: Guard,
        seen: MutableSet<Position> = mutableSetOf(),
        visited: MutableSet<Pair<Position, Direction>> = mutableSetOf(),
    ): TraverseResult {
        if (visited.contains(guard.pos to guard.dir)) return TraverseResult(seen.size, true)

        seen.add(guard.pos)
        visited.add(guard.pos to guard.dir)

        val next = guard.pos + guard.dir
        if (isOutOfBounds(next)) return TraverseResult(seen.size, false)
        if (grid[next] == '#') guard.dir = guard.dir.rotate()
        else guard.pos = next

        return traverse(grid, guard, seen, visited)
    }


    private fun allGrids() = sequence {
        for (position in positions()) {
            if (grid[position] == '.') yield(grid.map { it.toMutableList() }.apply { this[position] = '#' })
        }
    }


    private val rows = lines().size
    private val columns = lines().first().length

    private fun isOutOfBounds(pos: Position) = pos.r < 0 || pos.r >= rows || pos.c < 0 || pos.c >= columns

    private enum class Direction(val dr: Int, val dc: Int) {
        N(-1, 0), E(0, 1), S(1, 0), W(0, -1);
    }

    // matrices are hard
    private fun Direction.rotate() =
        when (this) {
            Direction.N -> Direction.E
            Direction.E -> Direction.S
            Direction.S -> Direction.W
            Direction.W -> Direction.N
        }

    private data class Position(val r: Int, val c: Int)

    private operator fun Position.plus(dir: Direction) = Position(r + dir.dr, c + dir.dc)

    private class Guard(var pos: Position, var dir: Direction)

    private fun Guard.copy() = Guard(pos, dir)

    private fun Position.toGuard(dir: Direction) = Guard(this, dir)

    private fun List<List<Char>>.scan(char: Char) = positions().firstOrNull { this[it] == char }
    private operator fun List<List<Char>>.get(at: Position) = this[at.r][at.c]
    private operator fun List<MutableList<Char>>.set(at: Position, char: Char) {
        this[at.r][at.c] = char
    }

    private fun positions() = sequence {
        for (r in 0 until rows) {
            for (c in 0 until columns) {
                yield(Position(r, c))
            }
        }
    }


}




