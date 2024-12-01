abstract class Puzzle(val rawInput: String) {
    abstract fun partOne(): String
    abstract fun partTwo(): String

    fun lines() = rawInput.trim().lines()
}
