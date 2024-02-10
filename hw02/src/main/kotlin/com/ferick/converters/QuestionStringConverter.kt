package com.ferick.converters

import com.ferick.model.Question
import org.springframework.stereotype.Component

@Component
class QuestionStringConverter {

    fun buildQuestionBlock(index: Int, question: Question): String {
        val answerBlock = buildAnswerBlock(question)
        return """Question #$index:
            |${question.text}
            |Possible answers:
            |$answerBlock
        """.trimMargin()
    }

    private fun buildAnswerBlock(question: Question) = question.answers
        .mapIndexed { i, answer -> (i + 1) to answer }
        .joinToString("\n") { "${it.first}. ${it.second.text}" }
}
