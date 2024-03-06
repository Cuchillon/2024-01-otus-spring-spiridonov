package com.ferick.service

interface LocalizedIOService : LocalizedMessagesService, IOService {

    fun printLineLocalized(code: String)

    fun readStringWithPromptLocalized(promptCode: String): String
}
