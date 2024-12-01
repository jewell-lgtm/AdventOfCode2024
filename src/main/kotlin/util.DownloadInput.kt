import java.net.HttpURLConnection
import java.net.URL

fun downloadInput(sessionCookie: String, year: Int, day: Int): String {
    val url = "https://adventofcode.com/$year/day/$day/input"
    val response = URL(url).openConnection() as HttpURLConnection
    response.requestMethod = "GET"
    response.setRequestProperty("Cookie", "session=$sessionCookie")
    response.connect()
    return response.inputStream.bufferedReader().readText()
}
