package com.ferick.helpers

import io.github.resilience4j.circuitbreaker.CallNotPermittedException
import org.assertj.core.api.Assertions.assertThatThrownBy
import java.lang.reflect.InvocationTargetException
import java.util.concurrent.TimeUnit

const val REPOSITORY_ERROR_MESSAGE = "Request to repository failed"

fun assertCircuitBreakerResponse(
    circuitBreakerName: String,
    block: () -> Any
) {
    val circuitBreakerErrorMessage =
        "CircuitBreaker '$circuitBreakerName' is OPEN and does not permit further calls"
    TimeUnit.MILLISECONDS.sleep(120L)
    assertThatThrownBy {
        rethrowingTarget { block.invoke() }
    }.isExactlyInstanceOf(RuntimeException::class.java).hasMessage(REPOSITORY_ERROR_MESSAGE)
    assertThatThrownBy {
        rethrowingTarget { block.invoke() }
    }.isExactlyInstanceOf(CallNotPermittedException::class.java).hasMessage(circuitBreakerErrorMessage)
    TimeUnit.MILLISECONDS.sleep(120L)
    assertThatThrownBy {
        rethrowingTarget { block.invoke() }
    }.isExactlyInstanceOf(RuntimeException::class.java).hasMessage(REPOSITORY_ERROR_MESSAGE)
}

private fun rethrowingTarget(block: () -> Any) {
    try {
        block.invoke()
    } catch (e: Exception) {
        when (e) {
            is InvocationTargetException -> throw e.targetException
            else -> throw e
        }
    }
}
