package com.ferick.service.impl

import com.ferick.converters.QuestionStringConverter
import com.ferick.dao.QuestionDao
import com.ferick.model.Student
import com.ferick.model.TestResult
import com.ferick.service.IOService
import com.ferick.service.TestService
import org.springframework.stereotype.Service

@Service
class TestServiceImpl(
    private val ioService: IOService,
    private val questionDao: QuestionDao,
    private val converter: QuestionStringConverter
) : TestService {

    override fun executeTestFor(student: Student): TestResult {
        ioService.printLine(
            """
                |Please answer the questions below
                |================================================================================
            """.trimMargin()
        )
        val questions = questionDao.findAll()
        val testResult = TestResult(student)
        questions.forEachIndexed { index, question ->
            val questionBlock = converter.buildQuestionBlock(index + 1, question)
            val answerNumber = ioService.readIntForRangeWithPrompt(
                MIN_ANSWER_NUMBER, question.answers.size, questionBlock, NUMBER_FORMAT_ERROR_MESSAGE
            )
            val isAnswerValid = question.answers[answerNumber - 1].isCorrect
            testResult.applyAnswer(question, isAnswerValid)
        }
        return testResult
    }

    companion object {
        private const val MIN_ANSWER_NUMBER = 1
        private const val NUMBER_FORMAT_ERROR_MESSAGE = "Wrong number format, write again, please:"
    }
}
