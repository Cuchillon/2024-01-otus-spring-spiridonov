package com.ferick.configuration

import com.ferick.configuration.properties.AppProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(AppProperties::class)
class AppConfiguration
