package com.ferick.dao

import com.ferick.configuration.TestFileNameProvider
import com.ferick.dao.dto.QuestionDto
import com.ferick.exceptions.QuestionReadException
import com.ferick.model.Question
import com.opencsv.bean.CsvToBeanBuilder
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.io.StringReader

class CsvQuestionDao(
    private val fileNameProvider: TestFileNameProvider
) : QuestionDao {

    override fun findAll(): List<Question> {
        val questionDtoList = try {
            val csv = getCsvContent(fileNameProvider.testFileName)
            val csvToBean = CsvToBeanBuilder<QuestionDto>(StringReader(csv))
                .withType(QuestionDto::class.java)
                .withSeparator(';')
                .withSkipLines(1)
                .build()
            csvToBean.parse()
        } catch (ex: Exception) {
            throw QuestionReadException("Reading questions from csv file failed", ex)
        }
        return questionDtoList.map { it.toDomainObject() }
    }

    private fun getCsvContent(fileName: String) = this::class.java.classLoader.getResourceAsStream(fileName)?.use {
        InputStreamReader(it).readText()
    } ?: throw FileNotFoundException("CSV file with questions not found")
}
