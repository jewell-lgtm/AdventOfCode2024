package year_2024

import Puzzle

class Day06(rawInput: String) : Puzzle(rawInput) {

    override fun partOne(): String {
        val grid = lines().map { it.toMutableList() }
        val steps = completeWalk(grid, grid.findGuard())
        return steps.distinctBy { it.x to it.y }.size.toString()
    }

    override fun partTwo(): String {
        val initialGuard = lines().map { it.toList() }.findGuard()

        // very dumb, very slow, but it works
        var result = 0
        for (y in yIndices) {
            println("y {$y} in {${yIndices.last()}}")
            for (x in xIndices) {
                val newGrid = lines().map { it.toMutableList() }.apply {
                    this[y][x] = '#'
                }
                val takenSteps = completeWalk(newGrid, initialGuard)
                if (takenSteps.containsDuplicate()) {
                    result++
                }
            }
        }
        return result.toString()
    }

    private fun completeWalk(
        grid: List<MutableList<Char>>,
        guard: Guard
    ): List<Guard> {
        var mutableGuard = guard
        grid[mutableGuard.y][mutableGuard.x] = 'X'
        val steps = mutableListOf(mutableGuard)
        while (mutableGuard.nextStepDoesNotExitMap()) {
            mutableGuard = mutableGuard.walk(grid)
            // cycle detected
            if (steps.contains(mutableGuard)) {
                steps.add(mutableGuard)
                break
            }
            steps.add(mutableGuard)
        }

        return steps
    }


    private enum class Direction(val dx: Int, val dy: Int) {
        N(0, -1), E(1, 0), S(0, 1), W(-1, 0);
    }

    private data class Guard(val direction: Direction, val x: Int, val y: Int)

    private fun Guard.walk(grid: List<MutableList<Char>>): Guard {
        val nextX = x + direction.dx
        val nextY = y + direction.dy
        return if (grid[nextY][nextX] != '#') {
            Guard(direction, nextX, nextY)
        } else {
            Guard(direction.rotate(), x, y)
        }
    }

    private val yIndices = lines().indices
    private val xIndices = lines()[0].indices

    private fun Guard.nextStepDoesNotExitMap(): Boolean {
        val nextX = x + direction.dx
        val nextY = y + direction.dy
        val result = nextY in yIndices && nextX in xIndices
        return result
    }

    private fun Direction.rotate() = when (this) {
        Direction.N -> Direction.E
        Direction.E -> Direction.S
        Direction.S -> Direction.W
        Direction.W -> Direction.N
    }

    private fun List<List<Char>>.findGuard(): Guard {
        for (y in indices) {
            for (x in this[y].indices) {
                val char = this[y][x]
                if (char == '^') {
                    return Guard(Direction.N, x, y)
                }
            }
        }
        error("No guard found")
    }

    private fun <E> List<E>.containsDuplicate(): Boolean {
        val seen = mutableSetOf<E>()
        for (element in this) {
            if (seen.contains(element)) {
                return true
            } else {
                seen.add(element)
            }
        }
        return false
    }

}
