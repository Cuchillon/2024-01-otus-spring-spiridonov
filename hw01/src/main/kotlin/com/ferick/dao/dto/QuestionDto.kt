package com.ferick.dao.dto

import com.ferick.model.Answer
import com.ferick.model.Question
import com.opencsv.bean.CsvBindAndSplitByPosition
import com.opencsv.bean.CsvBindByPosition

class QuestionDto {
    @CsvBindByPosition(position = 0)
    private lateinit var text: String

    @CsvBindAndSplitByPosition(
        position = 1,
        collectionType = ArrayList::class,
        elementType = Answer::class,
        converter = AnswerCsvConverter::class,
        splitOn = "\\|"
    )
    private lateinit var answers: List<Answer>

    fun toDomainObject(): Question = Question(text, answers)
}
