package com.ferick.service

import com.ferick.converters.QuestionStringConverter
import com.ferick.dao.QuestionDao
import com.ferick.model.Answer
import com.ferick.model.Question
import com.ferick.model.Student
import com.ferick.service.impl.TestServiceImpl
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import org.apache.commons.lang3.RandomStringUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TestServiceTest {

    private val ioService: IOService = mockk {
        justRun { printLine(any()) }
        every { readIntForRangeWithPrompt(any(), any(), any(), any()) } returns ANSWER_NUMBER
    }
    private val questionDao: QuestionDao = mockk()
    private val converter: QuestionStringConverter = mockk(relaxed = true)

    private val testService: TestService = TestServiceImpl(ioService, questionDao, converter)

    @Test
    fun executeTestFor() {
        val questions = getQuestions()
        val student = getStudent()
        every { questionDao.findAll() } returns questions
        val testResult = testService.executeTestFor(student)
        assertEquals(student, testResult.student)
        assertEquals(questions, testResult.answeredQuestions)
        assertEquals(RIGHT_ANSWER_COUNT, testResult.rightAnswersCount)
    }

    companion object {
        private const val ANSWER_NUMBER = 1
        private const val RIGHT_ANSWER_COUNT = 1

        private fun randomText() = RandomStringUtils.randomAlphabetic(8)

        private fun getQuestions() = listOf(
            Question(
                text = randomText(),
                answers = listOf(
                    Answer(
                        text = randomText(),
                        isCorrect = true
                    ),
                    Answer(
                        text = randomText(),
                        isCorrect = false
                    )
                )
            ),
            Question(
                text = randomText(),
                answers = listOf(
                    Answer(
                        text = randomText(),
                        isCorrect = false
                    ),
                    Answer(
                        text = randomText(),
                        isCorrect = true
                    )
                )
            )
        )

        private fun getStudent() = Student(
            firstName = randomText(),
            lastName = randomText()
        )
    }
}
