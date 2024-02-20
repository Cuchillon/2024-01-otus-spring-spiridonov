package com.ferick

import com.ferick.service.TestRunnerService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    val context = runApplication<Application>(*args)
    val testRunnerService = context.getBean(TestRunnerService::class.java)
    testRunnerService.run()
}
