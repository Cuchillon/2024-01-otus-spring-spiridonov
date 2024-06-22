package com.ferick.model.dto

data class RestApiError(
    val statusCode: Int,
    val errors: Map<String, String>
)
