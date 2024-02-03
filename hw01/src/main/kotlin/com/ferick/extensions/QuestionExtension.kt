package com.ferick.extensions

import com.ferick.model.Question

fun List<Question>.toQuestionBlock() = this.mapIndexed { index, question ->
    """Question #${index + 1}:
            |${question.text}
            |Possible answers:
            |${question.toAnswerBlock()}
        """.trimMargin()
}

private fun Question.toAnswerBlock() = this.answers
    .mapIndexed { i, answer -> (i + 1) to answer }
    .joinToString("\n") { "${it.first}. ${it.second.text}" }
