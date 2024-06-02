package com.ferick.repositories

import com.ferick.model.entities.Covering
import org.springframework.data.jpa.repository.JpaRepository

interface CoveringRepository : JpaRepository<Covering, Long> {

    fun findFirstByOrderByIdAsc(): Covering?
}
