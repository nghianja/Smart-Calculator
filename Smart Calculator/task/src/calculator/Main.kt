package calculator

import java.math.BigInteger
import java.util.Scanner

val assignments = mutableMapOf<String, BigInteger>()

fun <T> MutableList<T>.push(element: T): Boolean {
    return this.add(element)
}

fun <T> MutableList<T>.pop(): T? {
    val index = this.lastIndex
    if (index < 0) return null
    return this.removeAt(index)
}

fun <T> MutableList<T>.peek(): T? {
    val index = this.lastIndex
    if (index < 0) return null
    return this[index]
}

fun getPrecedence(character: Char): Int {
    return when (character) {
        '^' -> 3
        '*', '/' -> 2
        '+', '-' -> 1
        else -> -1
    }
}

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
            valstr.toBigIntegerOrNull() ?: throw Exception("Invalid assignment")
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
            "/help" -> println("The program calculates the sum of numbers. " +
                    "It supports multiplication, integer division, parentheses, " +
                    "addition, and both unary and binary minus operators.")
            else -> throw Exception("Unknown command")
        }
        return true
    }
    return false
}

fun toInfix(line: String): MutableList<String> {
    val infix = mutableListOf<String>()
    var temp = ""
    var prev: Char? = null
    var minus = 0
    for (i in line.indices) {
        when {
            line[i].isLetterOrDigit() -> {
                if (prev != null) {
                    if (prev == '-') {
                        if (minus % 2 == 0) prev = '+'
                        minus = 0
                    }
                    infix.add("$prev")
                    prev = null
                }
                temp += "${line[i]}"
            }
            else -> {
                if (temp.isNotEmpty()) {
                    infix.add(temp)
                    temp = ""
                }
                if (prev != null && prev == ')') {
                    infix.add("$prev")
                    prev = null
                }
                when (line[i]) {
                    '(' -> {
                        if (prev != null) {
                            if (prev == '-') {
                                if (minus % 2 == 0) prev = '+'
                                minus = 0
                            }
                            infix.add("$prev")
                        }
                        prev = line[i]
                    }
                    ')', '^', '*', '/' -> {
                        if (prev != null)
                            throw Exception("Invalid expression")
                        prev = line[i]
                    }
                    '+', '-' -> {
                        if (prev != null && prev != line[i])
                            throw Exception("Invalid expression")
                        if (line[i] == '-')
                            minus++
                        prev = line[i]
                    }
                }
            }
        }
    }
    if (temp.isNotEmpty()) {
        infix.add(temp)
    }
    if (prev != null) {
        if (prev == '-' && minus % 2 == 0) {
            prev = '+'
        }
        infix.add("$prev")
    }
    return infix
}

fun toPostfix(line: String): MutableList<String> {
    val infix = toInfix(line)
    val postfix = mutableListOf<String>()
    val stack = mutableListOf<Char>()
    for (i in infix.indices) {
        if (infix[i][0].isLetterOrDigit()) {
            postfix.push(infix[i])
        } else {
            when {
                stack.isEmpty() || stack.peek() == '(' -> stack.push(infix[i][0])
                infix[i][0] == '(' -> stack.push(infix[i][0])
                infix[i][0] == ')' -> {
                    while (stack.isNotEmpty() && stack.peek() != '(') {
                        postfix.push("${stack.pop()}")
                    }
                    if (stack.isEmpty()) throw Exception("Invalid expression")
                    stack.pop()
                }
                getPrecedence(stack.peek()!!) < getPrecedence(infix[i][0]) -> stack.push(infix[i][0])
                getPrecedence(stack.peek()!!) >= getPrecedence(infix[i][0]) -> {
                    while (stack.isNotEmpty() && stack.peek() != '(' &&
                            getPrecedence(stack.peek()!!) >= getPrecedence(infix[i][0])) {
                        postfix.push("${stack.pop()}")
                    }
                    stack.push(infix[i][0])
                }
            }
        }
    }
    while (stack.isNotEmpty()) {
        if (stack.peek() == '(') throw Exception("Invalid expression")
        postfix.push("${stack.pop()}")
    }
    return postfix
}

fun calculateSum(line: String) {
    val sum = mutableListOf<BigInteger>()
    val postfix = toPostfix(line)
    for (i in postfix.indices) {
        when {
            postfix[i][0].isDigit() -> sum.push(postfix[i].toBigInteger())
            postfix[i][0].isLetter() -> {
                val num = assignments[postfix[i]] ?: throw Exception("Unknown variable")
                sum.push(num)
            }
            else -> {
                val num1 = sum.pop()!!
                val num2 = sum.pop() ?: BigInteger.ZERO
                val num3: BigInteger = when (postfix[i][0]) {
                    '*' -> num2 * num1
                    '/' -> num2 / num1
                    '+' -> num2 + num1
                    '-' -> num2 - num1
                    else -> num2.pow(num1.toInt())
                }
                sum.push(num3)
            }
        }
    }
    println(sum.pop())
}

fun main() {
    val scanner = Scanner(System.`in`)
    while (true) {
        val line = scanner.nextLine().trim()
        try {
            if (line.isEmpty() || isCommand(line) || isAssignment(line))
                continue
            calculateSum(line)
        } catch (e: Exception) {
            println(e.message)
            if (e.message == "Bye!")
                break
        }
    }
}
