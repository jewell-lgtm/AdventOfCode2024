import io.github.cdimascio.dotenv.dotenv
import java.io.File
import java.time.LocalDate

val dotenv = dotenv()

fun main(args: Array<String>) {
    val day = getDay(args)
    val inputType = getInputType(args)
    val year = getYear()

    val input = getInput(year, day, inputType)
    val puzzleClass = Class.forName("year_$year.Day$day").asSubclass(Puzzle::class.java)
    val instance = puzzleClass.getConstructor(String::class.java).newInstance(input)

    var timeStarted = System.currentTimeMillis()
    println("Part One: ${instance.partOne()} (in ${System.currentTimeMillis() - timeStarted}ms)")
    timeStarted = System.currentTimeMillis()
    println("Part Two: ${instance.partTwo()} (in ${System.currentTimeMillis() - timeStarted}ms)")
}

private fun getYear() = dotenv.get("YEAR")?.toInt() ?: 2024

private fun getInputType(args: Array<String>) = args.getOrNull(1) ?: "input"

private fun getDay(args: Array<String>) = (
        args.getOrNull(0)?.toInt()
            ?: dotenv.get("DAY")?.toInt()
            ?: currentDay()
        ).toString().padStart(2, '0')

fun currentDay() = LocalDate.now().dayOfMonth.coerceAtMost(25)

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
