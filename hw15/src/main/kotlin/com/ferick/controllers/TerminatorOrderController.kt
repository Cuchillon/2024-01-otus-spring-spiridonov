package com.ferick.controllers

import com.ferick.model.entities.Terminator
import com.ferick.model.dto.TerminatorOrderRequest
import com.ferick.service.TerminatorOrderService
import jakarta.validation.Valid
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
    ) {
        return terminatorOrderService.construct(request)
    }
}
