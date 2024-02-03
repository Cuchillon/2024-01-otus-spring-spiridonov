package com.ferick.service.impl

import com.ferick.service.IOService
import com.ferick.service.TestService

class TestServiceImpl(
    private val ioService: IOService
) : TestService {

    override fun executeTest() {
        ioService.printLine("")
        ioService.printFormattedLine("Please answer the questions below%n")
        // Получить вопросы из дао и вывести их с вариантами ответов
    }
}
