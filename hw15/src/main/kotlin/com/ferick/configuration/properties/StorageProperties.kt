package com.ferick.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "storage")
class StorageProperties @ConstructorBinding constructor(
    val capacity: Int
)
