package year_2024

import Puzzle
import java.math.BigInteger

class Day09(rawInput: String) : Puzzle(rawInput) {


    override fun partOne(): String {
        val disk = diskBlocks()
        val blanks = disk.withIndex().filter { it.value.isEmpty() }.map { it.index }

        for (pos in blanks) {
            while (disk.last() == EmptyBlock) disk.removeLast()
            if (pos >= disk.size) break
            if (disk[pos] != EmptyBlock) error("Data corruption!")
            disk[pos] = disk.removeLast()
        }

        val result = disk.withIndex().sumOf {
            (it.value.read()?.toBigInteger() ?: error("Trying to read empty block")) * it.index.toBigInteger()
        }

        return result.toString()
    }


    override fun partTwo(): String {
        val disk = diskBlocks()
        val fileLocations = mutableMapOf<Int, IntRange>()
        var diskHead = 0
        while (diskHead < disk.size) {
            if (!disk[diskHead].isEmpty()) {
                val start = diskHead
                var end = diskHead
                val fileId = disk[diskHead].read()!!
                while (diskHead < disk.size && disk[diskHead].read() == fileId) end = diskHead++
                fileLocations[fileId] = IntRange(start, end)
            } else {
                diskHead++
            }
        }

        var blanks = getBlanks(disk)


        val filesToSort = fileLocations.keys.sorted().reversed()
            .map { Pair(it, IntRange(fileLocations[it]!!.first, fileLocations[it]!!.last)) }

        for ((fileKey, fileLocation) in filesToSort) {
            val fileSize = fileLocation.last - fileLocation.first + 1
            for (blank in blanks) {
                if (blank.first >= fileLocation.first) break
                val blankSize = blank.last - blank.first + 1
                if (blankSize >= fileSize) {
                    for (i in blank.first until blank.first + fileSize) {
                        val chunk = i - blank.first
                        disk[i] = DataBlock(fileKey)
                        disk[fileLocation.first + chunk] = EmptyBlock
                    }
                    break
                }
            }
            disk.dropLastWhile { it.isEmpty() }
            blanks = getBlanks(disk)
        }



        val result = disk.withIndex().sumOf {
            (it.value.read()?.toBigInteger() ?: BigInteger.ZERO) * it.index.toBigInteger()
        }

        return result.toString()
    }

    private fun diskBlocks(): MutableList<DiskBlock> {
        val disk = mutableListOf<DiskBlock>()
        var id = 0
        for ((index, char) in chars().withIndex()) {
            val x = char.toString().toInt()
            if (index % 2 == 0) {
                disk.addAll(List(size = x) { DataBlock(id) })
                id++
            } else {
                disk.addAll(List(size = x) { EmptyBlock })
            }
        }
        return disk
    }

    private fun getBlanks(disk: List<DiskBlock>): List<IntRange> {
        val result = mutableListOf<IntRange>()
        var i = 0
        while (i < disk.size) {
            if (disk[i].isEmpty()) {
                val start = i
                var end = i
                while (i < disk.size && disk[i].isEmpty()) end = i++
                result.add(IntRange(start, end))
            } else {
                i++
            }
        }
        return result.sortedBy { it.first }
    }


    sealed interface DiskBlock {
        fun isEmpty() = this is EmptyBlock
        fun read() = when (this) {
            is DataBlock -> this.fileId
            is EmptyBlock -> null
        }
    }

    data class DataBlock(val fileId: Int) : DiskBlock {
        override fun toString() = fileId.toString()
    }

    data object EmptyBlock : DiskBlock {
        override fun toString() = "."
    }


}
