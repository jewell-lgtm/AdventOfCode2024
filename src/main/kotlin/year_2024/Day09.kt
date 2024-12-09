package year_2024

import Puzzle
import java.math.BigInteger

class Day09(rawInput: String) : Puzzle(rawInput) {

    sealed interface DiskBlock {
        fun isEmpty() = this is EmptyBlock
        fun read(): BigInteger = when (this) {
            is DataBlock -> this.data
            is EmptyBlock -> error("reading an empty block")
        }
    }
    data class DataBlock(val data: BigInteger) : DiskBlock {
        override fun toString() = data.toString()
    }
    data object EmptyBlock : DiskBlock {
        override fun toString() = "."
    }

    override fun partOne(): String {
        val disk = mutableListOf<DiskBlock>()
        var id = BigInteger.ZERO
        for ((index, char) in chars().withIndex()) {
            val x = char.toString().toInt()
            if (index % 2 == 0) {
                disk.addAll(List(size = x) { DataBlock(id) })
                id++
            } else {
                disk.addAll(List(size = x) { EmptyBlock })
            }
        }
        val blanks = disk.withIndex().filter { it.value.isEmpty() }.map { it.index }

        for (pos in blanks) {
            while (disk.last() == EmptyBlock) disk.removeLast()
            if (pos >= disk.size) break
            if (disk[pos] != EmptyBlock) error("Data corruption!")
            disk[pos] = disk.removeLast()
        }

        val result = disk.withIndex().sumOf { (it.value.read()) * it.index.toBigInteger() }

        return result.toString()
    }

    override fun partTwo(): String {
        return "TODO"
    }


}
