package com.ferick.dao

import com.ferick.configuration.TestFileNameProvider
import com.ferick.model.Question

class CsvQuestionDao(
    private val fileNameProvider: TestFileNameProvider
) : QuestionDao {

    override fun findAll(): List<Question> {
        // Использовать CsvToBean
        // https://opencsv.sourceforge.net/#collection_based_bean_fields_one_to_many_mappings
        // Использовать QuestionReadException
        // Про ресурсы: https://mkyong.com/java/java-read-a-file-from-resources-folder/

        return emptyList()
    }
}
