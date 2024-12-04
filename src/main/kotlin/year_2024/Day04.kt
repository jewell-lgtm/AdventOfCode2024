package year_2024

import Puzzle

class Day04(rawInput: String) : Puzzle(rawInput) {
    override fun partOne(): String {
        val xs = lines().findChar('X')
        val xmases = xs.sumOf { x ->
            Direction.entries.mapNotNull { direction ->
                lines().walk(
                    x,
                    direction,
                    listOf('M', 'A', 'S')
                )
            }.size
        }

        return xmases.toString()
    }

    private val validCorners = listOf(
        listOf('M', 'S', 'S', 'M'),
        listOf('M', 'M', 'S', 'S'),
        listOf('S', 'M', 'M', 'S'),
        listOf('S', 'S', 'M', 'M')
    )


    override fun partTwo(): String {
        val charAs = lines().findChar('A')
        return (charAs.count { a -> validCorners.contains(lines().allCorners(a)) }).toString()
    }
}

private fun List<String>.allCorners(coord: Coord): List<Char> {
    val offsets = listOf(Direction.NW, Direction.NE, Direction.SE, Direction.SW)
    return offsets.mapNotNull { direction -> direction.offset(coord, this.width(), this.height())?.let { this.at(it) } }
}

private fun List<String>.width() = this[0].length
private fun List<String>.height() = this.size

private fun List<String>.walk(pos: Coord, direction: Direction, toFind: List<Char>): String? {
    if (toFind.isEmpty()) return "XMAS"
    val next = direction.offset(pos, this.width(), this.height())
    if (next != null) {
        val char = this.at(next)
        if (char == toFind.first()) return this.walk(next, direction, toFind.drop(1))
    }
    return null
}

enum class Direction(val x: Int, val y: Int) {
    N(0, -1),
    NE(1, -1),
    E(1, 0),
    SE(1, 1),
    S(0, 1),
    SW(-1, 1),
    W(-1, 0),
    NW(-1, -1);
}

private fun List<String>.at(pos: Coord): Char? = this.getOrNull(pos.y)?.getOrNull(pos.x)

private data class Coord(val x: Int, val y: Int)

private fun Direction.offset(coord: Coord, width: Int, height: Int): Coord? =
    Coord(coord.x + x, coord.y + y).takeIf { it.x in 0 until width && it.y in 0 until height }


private fun List<String>.findChar(c: Char): Set<Coord> =
    mutableSetOf<Coord>().apply {
        this@findChar.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                if (char == c) add(Coord(x, y))
            }
        }
    }


/*

orientations of X-MAS

M.S
.A.
M.S

M.M
.A.
S.S

S.M
.A.
S.M

S.S
.A.
M.M

 */


