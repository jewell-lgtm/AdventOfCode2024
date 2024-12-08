package year_2024

import Puzzle

class Day08(rawInput: String) : Puzzle(rawInput) {
    private val alphabet = ('a'..'z').toList() + ('A'..'Z').toList() + ('0'..'9').toList()

    override fun partOne(): String {
        val nodes = mutableSetOf<Vector2d>()
        for (letter in alphabet) {
            val found = lines().map { it.toList() }.findAll(letter).toPairs()
            for ((left, right) in found) {
                val distance = left - right
                (left + distance).takeIf { it.isValid() }
                    ?.apply { nodes.add(this) }
                (right - distance).takeIf { it.isValid() }
                    ?.apply { nodes.add(this) }
            }
        }
        return nodes.size.toString()
    }


    override fun partTwo(): String {
        val nodes = mutableSetOf<Vector2d>()
        for (letter in alphabet) {
            val found = lines().map { it.toList() }.findAll(letter).toPairs()
            if (found.isEmpty()) continue

            for ((leftPoint, rightPoint) in found) {
                val direction = (leftPoint - rightPoint).getDirection()
                var pos = leftPoint
                while (pos.isValid()) {
                    nodes.add(pos)
                    pos += direction
                }
                pos = rightPoint
                while (pos.isValid()) {
                    nodes.add(pos)
                    pos -= direction
                }
            }
        }
        return nodes.size.toString()
    }

    private data class Vector2d(val x: Int, val y: Int)

    private operator fun Vector2d.minus(distance: Vector2d): Vector2d {
        return Vector2d(x - distance.x, y - distance.y)
    }

    private operator fun Vector2d.plus(distance: Vector2d): Vector2d {
        return Vector2d(x + distance.x, y + distance.y)
    }

    private fun Vector2d.isValid() =
        y >= 0 && y < lines().size && x >= 0 && x < lines().first().length


    private fun List<List<Char>>.findAll(char: Char) = sequence {
        for (r in this@findAll.indices) {
            for (c in this@findAll[r].indices) {
                if (this@findAll[r][c] == char) yield(Vector2d(c, r))
            }
        }
    }.toList()

    private fun <E> List<E>.toPairs() = sequence {
        for (i in 0 until size - 1) {
            for (j in i + 1 until size) {
                yield(this@toPairs[i] to this@toPairs[j])
            }
        }
    }.toList()


    private fun Vector2d.getDirection(): Vector2d {
        // e.g. Vector2d(5,5) has direction Vector2d(1,1)
        // e.g. Vector2d(5,0) has direction Vector2d(1,0)
        // e.g. Vector2d(10,5) has direction Vector2d(2,1)
        val factor = this.x.hcf(this.y)
        return Vector2d(this.x / factor, this.y / factor)
    }

    private fun Int.hcf(other: Int): Int {
        var a = this
        var b = other
        while (b != 0) {
            val temp = b
            b = a % b
            a = temp
        }
        return a
    }
}


