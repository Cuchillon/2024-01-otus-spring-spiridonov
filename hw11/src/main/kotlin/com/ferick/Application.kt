package com.ferick

import com.github.cloudyrock.spring.v5.EnableMongock
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableMongock
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
