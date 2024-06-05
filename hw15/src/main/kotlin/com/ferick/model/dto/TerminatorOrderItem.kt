package com.ferick.model.dto

data class TerminatorOrderItem(
    val type: TerminatorType,
    val cpu: Cpu,
    val orderId: String
)
