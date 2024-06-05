package com.ferick.repositories

import com.ferick.model.entities.Skeleton
import org.springframework.data.jpa.repository.JpaRepository

interface SkeletonRepository : JpaRepository<Skeleton, Long> {

    fun findFirstByOrderByIdAsc(): Skeleton?
}
