package com.ferick.controllers

import com.ferick.model.dto.TerminatorDto
import com.ferick.model.dto.TerminatorOrderRequest
import com.ferick.model.dto.TerminatorOrderResponse
import com.ferick.service.TerminatorService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TerminatorController(
    private val terminatorService: TerminatorService
) {

    @PostMapping("/terminator")
    fun constructTerminators(
        @Valid @RequestBody request: TerminatorOrderRequest
    ): TerminatorOrderResponse {
        return terminatorService.construct(request)
    }

    @GetMapping("/terminator/{orderId}")
    fun findByOrderId(@PathVariable("orderId") orderId: String): List<TerminatorDto> {
        return terminatorService.findByOrderId(orderId)
    }
}
