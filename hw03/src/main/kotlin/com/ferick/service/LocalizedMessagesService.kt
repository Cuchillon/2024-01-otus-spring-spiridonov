package com.ferick.service

interface LocalizedMessagesService {

    fun getMessage(code: String, args: Array<Any> = emptyArray()): String
}
