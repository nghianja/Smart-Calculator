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
                    when (tokens[0]) {
                        "/exit" -> break@loop
                        "/help" -> println("The program calculates the sum of numbers")
                        else -> println(tokens[0].toInt())
                    }
            }
            else -> {
                var sum = 0
                for (i in 0 until tokens.size)
                    sum += tokens[i].toInt()
                println(sum)
            }
        }
    }
    println("Bye!")
}
