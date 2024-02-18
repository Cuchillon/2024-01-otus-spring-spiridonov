package com.ferick.service.impl

import com.ferick.service.IOService
import com.ferick.service.LocalizedIOService
import com.ferick.service.LocalizedMessagesService
import org.springframework.stereotype.Service

@Service
class LocalizedIOServiceImpl(
    private val localizedMessagesService: LocalizedMessagesService,
    private val ioService: IOService
) : LocalizedIOService {

    override fun printLine(line: String) {
        ioService.printLine(line)
    }

    override fun readString(): String {
        return ioService.readString()
    }

    override fun readStringWithPrompt(prompt: String): String {
        return ioService.readStringWithPrompt(prompt)
    }

    override fun readIntForRange(min: Int, max: Int, errorMessage: String): Int {
        return ioService.readIntForRange(min, max, errorMessage)
    }

    override fun readIntForRangeWithPrompt(min: Int, max: Int, prompt: String, errorMessage: String): Int {
        return ioService.readIntForRangeWithPrompt(min, max, prompt, errorMessage)
    }

    override fun printLineLocalized(code: String) {
        ioService.printLine(localizedMessagesService.getMessage(code))
    }

    override fun readStringWithPromptLocalized(promptCode: String): String {
        return ioService.readStringWithPrompt(localizedMessagesService.getMessage(promptCode))
    }

    override fun readIntForRangeLocalized(min: Int, max: Int, errorMessageCode: String): Int {
        return ioService.readIntForRange(min, max, localizedMessagesService.getMessage(errorMessageCode))
    }

    override fun readIntForRangeWithPrompLocalized(
        min: Int,
        max: Int,
        promptCode: String,
        errorMessageCode: String
    ): Int {
        return ioService.readIntForRangeWithPrompt(
            min,
            max,
            localizedMessagesService.getMessage(promptCode),
            localizedMessagesService.getMessage(errorMessageCode)
        )
    }

    override fun getMessage(code: String, args: Array<Any>): String {
        return localizedMessagesService.getMessage(code, args)
    }
}
