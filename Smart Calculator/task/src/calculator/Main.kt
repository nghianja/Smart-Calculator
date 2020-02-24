package calculator

import java.util.Scanner

fun isMinus(token: String): Boolean {
    return (token[0] == '-' && token.length % 2 == 1)
}

fun printSum(line: String) {
    val tokens = line.split("\\s+".toRegex())
    var sum = tokens[0].toInt()
    for (i in 1 until tokens.size step 2) {
        if (isMinus(tokens[i]))
            sum -= tokens[i + 1].toInt()
        else
            sum += tokens[i + 1].toInt()
    }
    println(sum)
}

fun main() {
    val scanner = Scanner(System.`in`)
    loop@ while (true) {
        val line = scanner.nextLine().trim()
        if (line.length == 0) continue
        else if (line.startsWith('/')) {
            when (line) {
                "/exit" -> break@loop
                "/help" -> println("The program calculates the sum of numbers. It supports both unary and binary minus operators.")
                else -> println("Unknown command")
            }
        } else {
            try {
                printSum(line)
            } catch (e: Exception) {
                println("Invalid expression")
            }
        }
    }
    println("Bye!")
}
