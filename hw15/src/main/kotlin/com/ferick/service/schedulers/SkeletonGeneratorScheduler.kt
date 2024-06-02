package com.ferick.service.schedulers

import com.ferick.configuration.properties.StorageProperties
import com.ferick.model.entities.Skeleton
import com.ferick.repositories.SkeletonRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class SkeletonGeneratorScheduler(
    private val storageProperties: StorageProperties,
    private val skeletonRepository: SkeletonRepository,
    private val schedulerDispatcher: CoroutineDispatcher
) {

    @Scheduled(fixedDelay = 1000)
    fun generate() = runBlocking(schedulerDispatcher) {
        if (skeletonRepository.count() < storageProperties.capacity) {
            skeletonRepository.save(Skeleton())
        }
    }
}
