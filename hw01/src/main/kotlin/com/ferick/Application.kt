package com.ferick

import com.ferick.service.TestRunnerService
import org.springframework.context.support.ClassPathXmlApplicationContext

fun main() {
    val context = ClassPathXmlApplicationContext("/spring-context.xml")
    val testRunnerService = context.getBean(TestRunnerService::class.java)
    testRunnerService.run()
}
