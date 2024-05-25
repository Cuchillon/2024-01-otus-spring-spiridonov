package com.ferick.service

import org.springframework.stereotype.Service

@Service
class CleanUpService(
    private val relationCache: RelationCache
) {

    fun cleanUp() {
        relationCache.clearRelations()
    }
}
