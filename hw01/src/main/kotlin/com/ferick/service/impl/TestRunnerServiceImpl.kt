package com.ferick.service.impl

import com.ferick.service.TestRunnerService
import com.ferick.service.TestService

class TestRunnerServiceImpl(
    private val testService: TestService
) : TestRunnerService {

    override fun run() {
        testService.executeTest()
    }
}
