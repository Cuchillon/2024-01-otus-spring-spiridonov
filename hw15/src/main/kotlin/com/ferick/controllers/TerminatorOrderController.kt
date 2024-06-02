package com.ferick.controllers

import com.ferick.model.dto.TerminatorDto
import com.ferick.model.dto.TerminatorOrderRequest
import com.ferick.model.dto.TerminatorOrderResponse
import com.ferick.service.TerminatorOrderService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TerminatorOrderController(
    private val terminatorOrderService: TerminatorOrderService
) {

    @PostMapping("/terminator")
    fun constructTerminators(
        @Valid @RequestBody request: TerminatorOrderRequest
    ): TerminatorOrderResponse {
        return terminatorOrderService.construct(request)
    }

    @GetMapping("/terminator/{orderId}")
    fun findByOrderId(@PathVariable("orderId") orderId: String): List<TerminatorDto> {
        return terminatorOrderService.findByOrderId(orderId)
    }
}
