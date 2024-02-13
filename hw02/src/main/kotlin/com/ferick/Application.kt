package com.ferick

import com.ferick.service.TestRunnerService
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.ComponentScan

@ComponentScan
class Application

fun main() {
    val context = AnnotationConfigApplicationContext(Application::class.java)
    val testRunnerService = context.getBean(TestRunnerService::class.java)
    testRunnerService.run()
}
