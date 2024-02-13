package com.ferick.service

interface IOService {

    fun printLine(line: String)

    fun readString(): String

    fun readStringWithPrompt(prompt: String): String

    fun readIntForRange(min: Int, max: Int, errorMessage: String): Int

    fun readIntForRangeWithPrompt(min: Int, max: Int, prompt: String, errorMessage: String): Int
}
