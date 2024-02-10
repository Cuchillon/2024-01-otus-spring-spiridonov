package com.ferick.service.impl

import com.ferick.service.ResultService
import com.ferick.service.StudentService
import com.ferick.service.TestRunnerService
import com.ferick.service.TestService
import org.springframework.stereotype.Service

@Service
class TestRunnerServiceImpl(
    private val testService: TestService,
    private val studentService: StudentService,
    private val resultService: ResultService
) : TestRunnerService {

    override fun run() {
        val student = studentService.determineCurrentStudent()
        val testResult = testService.executeTestFor(student)
        resultService.showResult(testResult)
    }
}
