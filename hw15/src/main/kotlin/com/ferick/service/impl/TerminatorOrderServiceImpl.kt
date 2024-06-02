package com.ferick.service.impl

import com.ferick.model.dto.Cpu
import com.ferick.model.entities.Terminator
import com.ferick.model.dto.TerminatorOrderItem
import com.ferick.model.dto.TerminatorOrderRequest
import com.ferick.model.dto.TerminatorType
import com.ferick.service.LaboratoryGateway
import com.ferick.service.TerminatorOrderService
import org.springframework.stereotype.Service

@Service
class TerminatorOrderServiceImpl(
    private val laboratoryGateway: LaboratoryGateway
) : TerminatorOrderService {

    override fun construct(request: TerminatorOrderRequest) {
        val items = request.order.flatMap { orderDetails ->
            (1..orderDetails.quantity).map {
                TerminatorOrderItem(
                    type = TerminatorType.valueOf(orderDetails.type),
                    cpu = Cpu()
                )
            }
        }
        return laboratoryGateway.process(items)
    }
}
