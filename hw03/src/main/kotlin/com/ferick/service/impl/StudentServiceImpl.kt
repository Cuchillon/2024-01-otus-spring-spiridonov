package com.ferick.service.impl

import com.ferick.model.Student
import com.ferick.service.LocalizedIOService
import com.ferick.service.StudentService
import org.springframework.stereotype.Service

@Service
class StudentServiceImpl(
    private val ioService: LocalizedIOService
) : StudentService {

    override fun determineCurrentStudent(): Student {
        val firstName = ioService.readStringWithPromptLocalized(FIRST_NAME_CODE)
        val lastName = ioService.readStringWithPromptLocalized(LAST_NAME_CODE)
        return Student(firstName, lastName)
    }

    companion object {
        private const val FIRST_NAME_CODE = "studentService.input.first.name"
        private const val LAST_NAME_CODE = "studentService.input.last.name"
    }
}
