package com.ferick.service

import com.ferick.model.dto.TerminatorOrderItem
import org.springframework.integration.annotation.Gateway
import org.springframework.integration.annotation.MessagingGateway


@MessagingGateway
interface LaboratoryGateway {

    @Gateway(requestChannel = "itemsChannel", replyChannel = "terminatorChannel")
    fun process(orderItem: Collection<TerminatorOrderItem>)
}
