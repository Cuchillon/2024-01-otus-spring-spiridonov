package com.ferick.service.impl

import com.ferick.exceptions.EntityNotFoundException
import com.ferick.model.dto.T1000Construction
import com.ferick.model.entities.Terminator
import com.ferick.model.dto.TerminatorOrderItem
import com.ferick.repositories.AlloyRepository
import com.ferick.service.AssemblingService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service("t1000AssemblingService")
class T1000AssemblingService(
    private val alloyRepository: AlloyRepository
) : AssemblingService {

    @Transactional
    override fun assemble(item: TerminatorOrderItem): Terminator {
        val alloy = alloyRepository.findFirstByOrderByIdAsc()
            ?: throw EntityNotFoundException("There are no alloys in the storage")
        val construction = T1000Construction(
            cpu = item.cpu,
            alloy = alloy
        )
        return Terminator(
            type = item.type,
            construction = construction,
            orderId = item.orderId
        ).also {
            alloyRepository.deleteById(alloy.id!!)
        }
    }
}
