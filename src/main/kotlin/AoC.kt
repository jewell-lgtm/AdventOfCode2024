import io.github.cdimascio.dotenv.dotenv
import java.io.File

val dotenv = dotenv()

fun main(args: Array<String>) {
    val day = (args.getOrNull(0)?.toInt() ?: 1).toString().padStart(2, '0')
    val inputType = args.getOrNull(1) ?: "input"
    val year = 2024

    val input = getInput(year, day, inputType)
    val puzzleClass = Class.forName("year_$year.Day$day").asSubclass(Puzzle::class.java)
    val instance = puzzleClass.getConstructor(String::class.java).newInstance(input)

    println("Part One: " + instance.partOne())
    println("Part Two: " + instance.partTwo())
}



fun getInput(year: Int, day: String, inputType: String): String {
    val inputFileLocation = "input/$year/day_${day}_$inputType.txt"
    val sessionCookie = dotenv.get("SESSION_COOKIE")
    if (inputType == "input") {
        if (!File(inputFileLocation).exists()) {
            val inputStr = downloadInput(sessionCookie, year, day.toInt())
            File(inputFileLocation).writeText(inputStr)
        }
    }
    return File(inputFileLocation).readText()
}
