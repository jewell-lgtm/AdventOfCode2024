abstract class Puzzle(val rawInput: String) {
    abstract fun partOne(): String
    abstract fun partTwo(): String

    fun chars() = rawInput.trim().toCharArray().toList()
    fun lines() = rawInput.trim().lines()
}
