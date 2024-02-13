package com.ferick.service.impl

import com.ferick.configuration.TestConfig
import com.ferick.model.TestResult
import com.ferick.service.IOService
import com.ferick.service.ResultService
import org.springframework.stereotype.Service

@Service
class ResultServiceImpl(
    private val testConfig: TestConfig,
    private val ioService: IOService
) : ResultService {

    override fun showResult(testResult: TestResult) {
        val student = testResult.student.getFullName()
        val answeredQuestionsCount = testResult.answeredQuestions.size
        val rightAnswersCount = testResult.rightAnswersCount
        val resultMessage = getResultMessage(rightAnswersCount)
        ioService.printLine(
            """
                |Test results
                |================================================================================
                |Student: $student
                |Answered questions count: $answeredQuestionsCount
                |Right answers count: $rightAnswersCount
                |--------------------------------------------------------------------------------
                |$resultMessage
            """.trimMargin()
        )
    }

    private fun getResultMessage(rightAnswersCount: Int) =
        if (rightAnswersCount >= testConfig.rightAnswersCountToPass) {
            SUCCESS_MESSAGE
        } else {
            FAILED_MESSAGE
        }

    companion object {
        private const val SUCCESS_MESSAGE = "Congratulations! You passed the test and you are a lucky guy!"
        private const val FAILED_MESSAGE = "Sorry. You failed the test and you are a miserable loser"
    }
}
