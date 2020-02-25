package calculator

import java.util.Scanner

val assignments = mutableMapOf<String, Int>()

fun isAlpha(line: String): Boolean {
    for (i in line.indices) {
        if (!line[i].isLetter())
            return false
    }
    return true
}

fun isAssignment(line: String): Boolean {
    val tokens = line.split("=")
    if (tokens.size == 2) {
        val key = tokens[0].trim()
        if (!isAlpha(key)) {
            throw Exception("Invalid identifier")
        }
        val valstr = tokens[1].trim()
        val value = if (!isAlpha(valstr)) {
            valstr.toIntOrNull() ?: throw Exception("Invalid assignment")
        } else {
            assignments[valstr] ?: throw Exception("Unknown variable")
        }
        assignments[key] = value
        return true
    } else if (tokens.size > 2) {
        throw Exception("Invalid assignment")
    }
    return false
}

fun isCommand(line: String): Boolean {
    if (line.startsWith('/')) {
        when (line) {
            "/exit" -> throw Exception("Bye!")
            "/help" -> println("The program calculates the sum of numbers. It supports both unary and binary minus operators.")
            else -> throw Exception("Unknown command")
        }
        return true
    }
    return false
}

fun isMinus(token: String): Boolean {
    return (token[0] == '-' && token.length % 2 == 1)
}

fun printSum(line: String) {
    val tokens = line.split("\\s+".toRegex())
    var sum = if (!isAlpha(tokens[0])) {
        tokens[0].toIntOrNull() ?: throw Exception("Invalid expression")
    } else {
        assignments[tokens[0]] ?: throw Exception("Unknown variable")
    }
    for (i in 1 until tokens.size step 2) {
        val valstr = tokens[i + 1]
        val value = if (!isAlpha(valstr)) {
            valstr.toIntOrNull() ?: throw Exception("Invalid expression")
        } else {
            assignments[valstr] ?: throw Exception("Unknown variable")
        }
        if (isMinus(tokens[i]))
            sum -= value
        else
            sum += value
    }
    println(sum)
}

fun main() {
    val scanner = Scanner(System.`in`)
    while (true) {
        val line = scanner.nextLine().trim()
        try {
            if (line.isEmpty() || isCommand(line) || isAssignment(line))
                continue
            printSum(line)
        } catch (e: Exception) {
            println(e.message)
            if (e.message == "Bye!")
                break
        }
    }
}
