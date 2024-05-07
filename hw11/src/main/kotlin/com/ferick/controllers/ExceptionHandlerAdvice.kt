package com.ferick.controllers

import com.ferick.exceptions.EntityNotFoundException
import com.ferick.model.dto.RestApiError
import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException


@RestControllerAdvice
class ExceptionHandlerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WebExchangeBindException::class)
    fun handleValidationExceptions(
        ex: WebExchangeBindException
    ): RestApiError {
        val errors = ex.bindingResult.allErrors.associate { error ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.getDefaultMessage() ?: ""
            fieldName to errorMessage
        }
        return RestApiError(HttpStatus.BAD_REQUEST.value(), errors)
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException::class)
    fun handleNotFoundException(
        ex: EntityNotFoundException
    ): RestApiError {
        return RestApiError(HttpStatus.NOT_FOUND.value(), mapOf("message" to ex.message!!))
    }
}
