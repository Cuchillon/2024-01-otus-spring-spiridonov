package com.ferick.service

interface LocalizedMessagesService {

    fun getMessage(code: String, vararg args: Any): String
}
