package year_2024

import Puzzle

class Day06(rawInput: String) : Puzzle(rawInput) {

    override fun partOne(): String {
        val grid = lines().map { it.toMutableList() }
        val guard = grid.findGuard()

        grid[guard.y][guard.x] = 'X'
        while (guard.nextStepIsNotOffMap()) {
            guard.walk(grid)
        }

        return grid.flatten().count { it == 'X' }.toString()

    }


    private enum class Direction(val dx: Int, val dy: Int) {
        N(0, -1), E(1, 0), S(0, 1), W(-1, 0);
    }

    private data class Guard(var direction: Direction, var x: Int, var y: Int)

    private fun Guard.walk(grid: List<MutableList<Char>>) {
        val nextX = x + direction.dx
        val nextY = y + direction.dy
        if (grid[nextY][nextX] != '#') {
            x = nextX
            y = nextY
            grid[y][x] = 'X'


        } else {
            rotate()
        }
    }

    private fun Guard.nextStepIsNotOffMap(): Boolean {
        val nextX = x + direction.dx
        val nextY = y + direction.dy
        val result = nextY in lines().indices && nextX in lines()[nextY].indices

        return result
    }

    private fun Guard.rotate() {
        direction = when (direction) {
            Direction.N -> Direction.E
            Direction.E -> Direction.S
            Direction.S -> Direction.W
            Direction.W -> Direction.N
        }
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

    override fun partTwo(): String {
        return "TODO"
    }
}
