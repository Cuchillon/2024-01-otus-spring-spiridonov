package com.ferick.service.schedulers

import com.ferick.model.entities.Skeleton
import com.ferick.repositories.SkeletonRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class SkeletonGeneratorScheduler(
    private val skeletonRepository: SkeletonRepository,
    private val schedulerDispatcher: CoroutineDispatcher
) {

    @Scheduled(fixedDelay = 1000)
    fun generate() = runBlocking(schedulerDispatcher) {
        if (skeletonRepository.count() < 50) {
            skeletonRepository.save(Skeleton())
        }
    }
}
