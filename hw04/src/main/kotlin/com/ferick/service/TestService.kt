package com.ferick.service

import com.ferick.model.Student
import com.ferick.model.TestResult

interface TestService {

    fun executeTestFor(student: Student): TestResult
}
