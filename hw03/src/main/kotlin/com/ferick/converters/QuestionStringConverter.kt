package com.ferick.converters

import com.ferick.model.Question
import com.ferick.service.LocalizedMessagesService
import org.springframework.stereotype.Component

@Component
class QuestionStringConverter(
    private val localizedMessagesService: LocalizedMessagesService
) {

    fun buildQuestionBlock(index: Int, question: Question): String {
        val answerBlock = buildAnswerBlock(question)
        val questionHeader = localizedMessagesService.getMessage(QUESTION_CODE)
        val possibleAnswersHeader = localizedMessagesService.getMessage(POSSIBLE_ANSWERS_CODE)
        return """$questionHeader #$index:
            |${question.text}
            |$possibleAnswersHeader:
            |$answerBlock
        """.trimMargin()
    }

    private fun buildAnswerBlock(question: Question) = question.answers
        .mapIndexed { i, answer -> (i + 1) to answer }
        .joinToString("\n") { "${it.first}. ${it.second.text}" }

    companion object {
        private const val QUESTION_CODE = "questionStringConverter.question"
        private const val POSSIBLE_ANSWERS_CODE = "questionStringConverter.possible.answers"
    }
}
