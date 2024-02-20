package com.ferick.service.impl

import com.ferick.converters.QuestionStringConverter
import com.ferick.dao.QuestionDao
import com.ferick.model.Question
import com.ferick.model.Student
import com.ferick.model.TestResult
import com.ferick.service.LocalizedIOService
import com.ferick.service.TestService
import org.springframework.stereotype.Service

@Service
class TestServiceImpl(
    private val ioService: LocalizedIOService,
    private val questionDao: QuestionDao,
    private val converter: QuestionStringConverter
) : TestService {

    override fun executeTestFor(student: Student): TestResult {
        val answerQuestions = ioService.getMessage(ANSWER_QUESTIONS_CODE)
        ioService.printLine(
            """
                |$answerQuestions
                |================================================================================
            """.trimMargin()
        )
        val questions = questionDao.findAll()
        return getTestResult(student, questions)
    }

    private fun getTestResult(student: Student, questions: List<Question>): TestResult {
        val testResult = TestResult(student)
        questions.forEachIndexed { index, question ->
            val questionBlock = converter.buildQuestionBlock(index + 1, question)
            val numberFormatErrorMessage = ioService.getMessage(NUMBER_FORMAT_ERROR_MESSAGE_CODE)
            val answerNumber = ioService.readIntForRangeWithPrompt(
                MIN_ANSWER_NUMBER, question.answers.size, questionBlock, numberFormatErrorMessage
            )
            val isAnswerValid = question.answers[answerNumber - 1].isCorrect
            testResult.applyAnswer(question, isAnswerValid)
        }
        return testResult
    }

    companion object {
        private const val MIN_ANSWER_NUMBER = 1
        private const val ANSWER_QUESTIONS_CODE = "testService.answer.questions"
        private const val NUMBER_FORMAT_ERROR_MESSAGE_CODE = "testService.wrong.number.format"
    }
}
