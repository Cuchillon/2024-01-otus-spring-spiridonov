package com.ferick.service.impl

import com.ferick.service.IOService
import java.io.PrintStream

class StreamsIOServiceImpl(
    private val printStream: PrintStream
) : IOService {

    override fun printLine(line: String) {
        printStream.println(line)
    }

    override fun printFormattedLine(line: String, vararg args: Any) {
        printStream.printf("$line%n", args)
    }
}
