package com.ferick.service.impl

import com.ferick.service.IOService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.InputStream
import java.io.PrintStream
import java.util.Scanner

@Service
class StreamsIOServiceImpl(
    @Value("#{T(System).out}")
    private val printStream: PrintStream,
    @Value("#{T(System).in}")
    inputStream: InputStream
) : IOService {

    private val scanner = Scanner(inputStream)

    override fun printLine(line: String) {
        printStream.println(line)
    }

    override fun readString(): String = scanner.nextLine()

    override fun readStringWithPrompt(prompt: String): String {
        printLine(prompt)
        return scanner.nextLine()
    }

    override fun readIntForRange(min: Int, max: Int, errorMessage: String): Int {
        repeat(MAX_ATTEMPTS) {
            try {
                printStream.print("Your answer: ")
                val intValue = scanner.nextLine().toInt()
                if (intValue < min || intValue > max) {
                    throw IllegalArgumentException()
                }
                return intValue
            } catch (_: IllegalArgumentException) {
                printLine(errorMessage)
            }
        }
        throw IllegalArgumentException("Error during reading int value")
    }

    override fun readIntForRangeWithPrompt(min: Int, max: Int, prompt: String, errorMessage: String): Int {
        printLine(prompt)
        return readIntForRange(min, max, errorMessage)
    }

    companion object {
        private const val MAX_ATTEMPTS = 10
    }
}
