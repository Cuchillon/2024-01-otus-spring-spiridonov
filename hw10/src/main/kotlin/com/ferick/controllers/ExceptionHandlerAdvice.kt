package com.ferick.controllers

import com.ferick.exceptions.EntityNotFoundException
import com.ferick.model.dto.RestApiError
import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.function.Consumer


@RestControllerAdvice
class ExceptionHandlerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(
        ex: MethodArgumentNotValidException
    ): RestApiError {
        val errors: MutableMap<String, String> = HashMap()
        ex.bindingResult.allErrors.forEach(Consumer { error: ObjectError ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.getDefaultMessage() ?: ""
            errors[fieldName] = errorMessage
        })
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