package com.ferick.service

import com.ferick.model.entities.Terminator
import com.ferick.model.dto.TerminatorOrderRequest

interface TerminatorOrderService {

    fun construct(request: TerminatorOrderRequest)
}
