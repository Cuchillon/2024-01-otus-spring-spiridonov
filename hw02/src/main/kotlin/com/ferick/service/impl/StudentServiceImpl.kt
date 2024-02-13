package com.ferick.service.impl

import com.ferick.model.Student
import com.ferick.service.IOService
import com.ferick.service.StudentService
import org.springframework.stereotype.Service

@Service
class StudentServiceImpl(
    private val ioService: IOService
) : StudentService {

    override fun determineCurrentStudent(): Student {
        val firstName = ioService.readStringWithPrompt("Please input your first name:")
        val lastName = ioService.readStringWithPrompt("Please input your last name:")
        return Student(firstName, lastName)
    }
}
