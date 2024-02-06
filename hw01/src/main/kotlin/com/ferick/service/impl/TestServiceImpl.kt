package com.ferick.service.impl

import com.ferick.dao.QuestionDao
import com.ferick.extensions.toQuestionBlock
import com.ferick.service.IOService
import com.ferick.service.TestService

class TestServiceImpl(
    private val ioService: IOService,
    private val questionDao: QuestionDao
) : TestService {

    override fun executeTest() {
        ioService.printLine(
            """
                |Please answer the questions below
                |================================================================================
                |${questionDao.findAll().toQuestionBlock().joinToString("\n\n") { it }}
                |================================================================================
            """.trimMargin()
        )
    }
}
