package calculator

import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)
    loop@ while (true) {
        val line = scanner.nextLine().trim()
        if (line.length == 0) continue
        val tokens = line.split(" ")
        when (tokens.size) {
            1 -> {
                    if (tokens[0] == "/exit") break@loop
                    println(tokens[0].toInt())
            }
            2 -> println(tokens[0].toInt() + tokens[1].toInt())
        }
    }
    println("Bye!")
}
