package com.ferick.service.impl

import com.ferick.configuration.properties.TestConfig
import com.ferick.model.TestResult
import com.ferick.service.LocalizedIOService
import com.ferick.service.ResultService
import org.springframework.stereotype.Service

@Service
class ResultServiceImpl(
    private val testConfig: TestConfig,
    private val ioService: LocalizedIOService
) : ResultService {

    override fun showResult(testResult: TestResult) {
        val testResultHeader = ioService.getMessage(TEST_RESULTS_CODE)
        val student = ioService.getMessage(STUDENT_CODE, arrayOf(testResult.student.getFullName()))
        val answeredQuestionsCount =
            ioService.getMessage(ANSWERED_QUESTION_COUNT_CODE, arrayOf(testResult.answeredQuestions.size))
        val rightAnswersCount = ioService.getMessage(RIGHT_ANSWERS_COUNT_CODE, arrayOf(testResult.rightAnswersCount))
        val resultMessage = getResultMessage(testResult.rightAnswersCount)
        ioService.printLine(
            """
                |$testResultHeader
                |================================================================================
                |$student
                |$answeredQuestionsCount
                |$rightAnswersCount
                |--------------------------------------------------------------------------------
                |$resultMessage
            """.trimMargin()
        )
    }

    private fun getResultMessage(rightAnswersCount: Int) =
        if (rightAnswersCount >= testConfig.rightAnswersCountToPass) {
            ioService.getMessage(SUCCESS_MESSAGE_CODE)
        } else {
            ioService.getMessage(FAILED_MESSAGE_CODE)
        }

    companion object {
        private const val TEST_RESULTS_CODE = "resultService.test.results"
        private const val STUDENT_CODE = "resultService.student"
        private const val ANSWERED_QUESTION_COUNT_CODE = "resultService.answered.questions.count"
        private const val RIGHT_ANSWERS_COUNT_CODE = "resultService.right.answers.count"
        private const val SUCCESS_MESSAGE_CODE = "resultService.success.message"
        private const val FAILED_MESSAGE_CODE = "resultService.failed.message"
    }
}
