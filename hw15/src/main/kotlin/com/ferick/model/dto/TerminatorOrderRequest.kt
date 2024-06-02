package com.ferick.model.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Positive

data class TerminatorOrderRequest(
    @NotEmpty(message = "Order set must not be empty")
    val order: Set<OrderDetails>
)

data class OrderDetails(
    @Pattern(regexp = "T_800|T_1000")
    val type: String,
    @NotNull
    @Positive(message = "Quantity must be positive")
    val quantity: Int
)
