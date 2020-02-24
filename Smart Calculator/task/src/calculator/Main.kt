package calculator

import java.util.Scanner

fun isMinus(token: String): Boolean {
    return (token[0] == '-' && token.length % 2 == 1)
}

fun main() {
    val scanner = Scanner(System.`in`)
    loop@ while (true) {
        val line = scanner.nextLine().trim()
        if (line.length == 0) continue
        val tokens = line.split("\\s+".toRegex())
        if (tokens.size == 1) {
            when (tokens[0]) {
                "/exit" -> break@loop
                "/help" -> println("The program calculates the sum of numbers. It supports both unary and binary minus operators.")
                else -> println(tokens[0].toInt())
            }
        }
        else {
            var sum = tokens[0].toInt()
            for (i in 1 until tokens.size step 2) {
                if (isMinus(tokens[i]))
                    sum -= tokens[i + 1].toInt()
                else
                    sum += tokens[i + 1].toInt()
            }
            println(sum)
        }
    }
    println("Bye!")
}
