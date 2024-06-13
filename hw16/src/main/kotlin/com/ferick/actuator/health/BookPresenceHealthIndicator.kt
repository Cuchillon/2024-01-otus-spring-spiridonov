package com.ferick.actuator.health

import com.ferick.repositories.BookRepository
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.boot.actuate.health.Status
import org.springframework.stereotype.Component

@Component
class BookPresenceHealthIndicator(
    private val bookRepository: BookRepository
) : HealthIndicator {

    override fun health(): Health {
        val bookCount = bookRepository.count()
        return if (bookCount < 1) {
            Health.down()
                .status(Status.DOWN)
                .withDetail("count", bookCount)
                .build()
        } else {
            Health.up()
                .status(Status.UP)
                .withDetail("count", bookCount)
                .build()
        }
    }
}
