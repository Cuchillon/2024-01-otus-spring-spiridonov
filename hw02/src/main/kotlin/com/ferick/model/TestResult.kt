package com.ferick.model

data class TestResult(
    val student: Student,
    val answeredQuestions: MutableList<Question> = mutableListOf(),
    var rightAnswersCount: Int
) {
    fun applyAnswer(question: Question, isRightAnswer: Boolean) {
        answeredQuestions.add(question)
        if (isRightAnswer) {
            rightAnswersCount++
        }
    }
}
