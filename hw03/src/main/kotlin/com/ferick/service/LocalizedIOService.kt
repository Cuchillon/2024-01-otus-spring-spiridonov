package com.ferick.service

interface LocalizedIOService : LocalizedMessagesService, IOService {

    fun printLineLocalized(code: String)

    fun readStringWithPromptLocalized(promptCode: String): String

    fun readIntForRangeLocalized(min: Int, max: Int, errorMessageCode: String): Int

    fun readIntForRangeWithPrompLocalized(min: Int, max: Int, promptCode: String, errorMessageCode: String): Int
}
