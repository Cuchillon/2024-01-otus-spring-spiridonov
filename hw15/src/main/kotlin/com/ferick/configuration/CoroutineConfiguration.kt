package com.ferick.configuration

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import java.util.concurrent.Executors

@Configuration
@EnableScheduling
class CoroutineConfiguration {

    @Bean
    fun schedulerDispatcher(): CoroutineDispatcher {
        return Executors.newFixedThreadPool(3).asCoroutineDispatcher()
    }
}
