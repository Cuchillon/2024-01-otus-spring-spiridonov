package com.ferick.service

import com.ferick.dao.QuestionDao
import com.ferick.model.Answer
import com.ferick.model.Question
import com.ferick.model.Student
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.justRun
import org.apache.commons.lang3.RandomStringUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TestServiceTest {

    @Autowired
    private lateinit var testService: TestService

    @MockkBean
    private lateinit var ioService: LocalizedIOService

    @MockkBean
    private lateinit var questionDao: QuestionDao

    @Test
    fun executeTestFor() {
        val questions = getQuestions()
        val student = getStudent()
        justRun { ioService.printLine(any()) }
        every { ioService.readIntForRangeWithPrompt(any(), any(), any(), any()) } returns ANSWER_NUMBER
        every { ioService.getMessage(any()) } returns randomText()
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
