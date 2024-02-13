package com.ferick.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

@Component
@PropertySource("classpath:application.properties")
data class AppProperties(
    @Value("\${test.fileName}")
    override val testFileName: String,
    @Value("\${test.rightAnswersCountToPass}")
    override val rightAnswersCountToPass: Int
) : TestConfig, TestFileNameProvider
