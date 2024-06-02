package com.ferick.service.impl

import com.ferick.exceptions.EntityNotFoundException
import com.ferick.model.dto.T800Construction
import com.ferick.model.entities.Terminator
import com.ferick.model.dto.TerminatorOrderItem
import com.ferick.repositories.CoveringRepository
import com.ferick.repositories.SkeletonRepository
import com.ferick.service.AssemblingService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service("t800AssemblingService")
class T800AssemblingService(
    private val skeletonRepository: SkeletonRepository,
    private val coveringRepository: CoveringRepository
) : AssemblingService {

    @Transactional
    override fun assemble(item: TerminatorOrderItem): Terminator {
        val skeleton = skeletonRepository.findFirstByOrderByIdAsc()
            ?: throw EntityNotFoundException("There are no skeletons in the storage")
        val covering = coveringRepository.findFirstByOrderByIdAsc()
            ?: throw EntityNotFoundException("There are no covering in the storage")
        val construction = T800Construction(
            cpu = item.cpu,
            skeleton = skeleton,
            covering = covering
        )
        return Terminator(
            type = item.type,
            construction = construction,
            orderId = item.orderId
        ).also {
            skeletonRepository.deleteById(skeleton.id!!)
            coveringRepository.deleteById(covering.id!!)
        }
    }
}
