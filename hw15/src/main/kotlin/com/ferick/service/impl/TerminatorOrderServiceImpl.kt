package com.ferick.service.impl

import com.ferick.converters.TerminatorConverter
import com.ferick.model.dto.Cpu
import com.ferick.model.dto.TerminatorDto
import com.ferick.model.dto.TerminatorOrderItem
import com.ferick.model.dto.TerminatorOrderRequest
import com.ferick.model.dto.TerminatorOrderResponse
import com.ferick.model.dto.TerminatorType
import com.ferick.repositories.TerminatorRepository
import com.ferick.service.LaboratoryGateway
import com.ferick.service.TerminatorOrderService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class TerminatorOrderServiceImpl(
    private val laboratoryGateway: LaboratoryGateway,
    private val terminatorRepository: TerminatorRepository,
    private val terminatorConverter: TerminatorConverter
) : TerminatorOrderService {

    override fun construct(request: TerminatorOrderRequest): TerminatorOrderResponse {
        val orderId = UUID.randomUUID().toString()
        val items = request.order.flatMap { orderDetails ->
            (1..orderDetails.quantity!!).map {
                TerminatorOrderItem(
                    type = TerminatorType.valueOf(orderDetails.type!!),
                    cpu = Cpu(),
                    orderId = orderId
                )
            }
        }
        laboratoryGateway.process(items)
        return TerminatorOrderResponse(orderId)
    }

    @Transactional(readOnly = true)
    override fun findByOrderId(orderId: String): List<TerminatorDto> {
        return terminatorRepository.findByOrderId(orderId).map {
            terminatorConverter.toDto(it)
        }
    }
}
