package com.ferick.model.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Positive

class TerminatorOrderRequest {
    @NotEmpty(message = "Order set must not be empty")
    @Valid
    val order: Set<OrderDetails> = emptySet()
}

class OrderDetails {
    @NotNull
    @Pattern(regexp = "(T_800|T_1000)", message = "Type must be only T_800 or T_1000")
    val type: String? = null

    @NotNull
    @Positive(message = "Quantity must be positive")
    val quantity: Int? = null
}
