package com.ferick.service

import com.ferick.model.dto.TerminatorDto
import com.ferick.model.dto.TerminatorOrderRequest
import com.ferick.model.dto.TerminatorOrderResponse

interface TerminatorService {

    fun construct(request: TerminatorOrderRequest): TerminatorOrderResponse

    fun findByOrderId(orderId: String): List<TerminatorDto>
}
