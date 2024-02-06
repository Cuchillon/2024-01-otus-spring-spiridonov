package com.ferick.dao

import com.ferick.configuration.TestFileNameProvider
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class QuestionDaoTest {

    private val fileNameProvider: TestFileNameProvider = mockk {
        every { testFileName } returns TEST_FILE_NAME
    }

    private val questionDao: QuestionDao = CsvQuestionDao(fileNameProvider)

    @Test
    fun findAll() {
        val questions = questionDao.findAll()
        assertEquals(QUESTIONS_SIZE, questions.size)
        assertTrue(questions.any { it.text == TEST_QUESTION })
        assertTrue(questions.find { it.text == TEST_QUESTION }!!.answers.any { it.text == TEST_CORRECT_ANSWER })
        assertTrue(
            questions.find { it.text == TEST_QUESTION }!!.answers.find { it.text == TEST_CORRECT_ANSWER }!!.isCorrect
        )
    }

    companion object {
        private const val TEST_FILE_NAME = "questions.csv"
        private const val QUESTIONS_SIZE = 5
        private const val TEST_QUESTION = "Is a serious face a sign of intelligence?"
        private const val TEST_CORRECT_ANSWER =
            "No, all the stupid things in the world are done with just such a facial expression"
    }
}
