package com.ferick.service.schedulers

import com.ferick.configuration.properties.StorageProperties
import com.ferick.model.entities.Alloy
import com.ferick.repositories.AlloyRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class AlloyGeneratorScheduler(
    private val storageProperties: StorageProperties,
    private val alloyRepository: AlloyRepository,
    private val schedulerDispatcher: CoroutineDispatcher
) {

    @Scheduled(fixedDelay = 1000)
    fun generate() = runBlocking(schedulerDispatcher) {
        if (alloyRepository.count() < storageProperties.capacity) {
            alloyRepository.save(Alloy())
        }
    }
}
