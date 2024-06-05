package com.ferick.configuration

import com.ferick.configuration.properties.ThreadPoolProperties
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import java.util.concurrent.Executors

@Configuration
@EnableScheduling
@EnableConfigurationProperties(ThreadPoolProperties::class)
class CoroutineConfiguration(
    private val threadPoolProperties: ThreadPoolProperties
) {

    @Bean
    fun schedulerDispatcher(): CoroutineDispatcher {
        return Executors.newFixedThreadPool(threadPoolProperties.threadNumber).asCoroutineDispatcher()
    }
}
