package com.ferick.service.schedulers

import com.ferick.configuration.properties.StorageProperties
import com.ferick.model.entities.Covering
import com.ferick.repositories.CoveringRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class CoveringGeneratorScheduler(
    private val storageProperties: StorageProperties,
    private val coveringRepository: CoveringRepository,
    private val schedulerDispatcher: CoroutineDispatcher
) {

    @Scheduled(fixedDelay = 1000)
    fun generate() = runBlocking(schedulerDispatcher) {
        if (coveringRepository.count() < storageProperties.capacity) {
            coveringRepository.save(Covering())
        }
    }
}
