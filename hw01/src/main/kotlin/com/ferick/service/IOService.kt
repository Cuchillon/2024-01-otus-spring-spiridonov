package com.ferick.service

interface IOService {

    fun printLine(line: String)

    fun printFormattedLine(line: String, vararg args: Any)
}
