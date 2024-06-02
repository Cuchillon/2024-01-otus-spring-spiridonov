package com.ferick.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "scheduler-thread-pool")
class ThreadPoolProperties @ConstructorBinding constructor(
    val threadNumber: Int
)
