package com.ferick.repositories

import com.ferick.model.entities.Alloy
import org.springframework.data.jpa.repository.JpaRepository

interface AlloyRepository : JpaRepository<Alloy, Long> {

    fun findFirstByOrderByIdAsc(): Alloy?
}
