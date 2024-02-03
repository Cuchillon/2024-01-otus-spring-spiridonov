package com.ferick.service.impl

import com.ferick.dao.QuestionDao
import com.ferick.model.Question
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
                |${formQuestionItems(questionDao.findAll()).joinToString("\n") { it }}
                |================================================================================
            """.trimMargin()
        )
    }

    private fun formQuestionItems(questions: List<Question>) = questions.mapIndexed { index, question ->
        """Question #${index + 1}:
            |${question.text}
            |Possible answers:
            |${question.answers.joinToString("\n") { it.text }}
        """.trimMargin()
    }
}
