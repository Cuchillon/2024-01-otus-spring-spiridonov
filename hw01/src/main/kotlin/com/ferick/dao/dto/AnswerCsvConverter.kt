package com.ferick.dao.dto

import com.ferick.model.Answer
import com.opencsv.bean.AbstractCsvConverter

class AnswerCsvConverter : AbstractCsvConverter() {

    override fun convertToRead(value: String): Any {
        val valueArr = value.split("%")
        return Answer(valueArr[0], valueArr[1].toBoolean())
    }
}
