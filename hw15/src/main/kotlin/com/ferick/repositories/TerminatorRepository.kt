package com.ferick.repositories

import com.ferick.model.entities.Terminator
import org.springframework.data.jpa.repository.JpaRepository

interface TerminatorRepository : JpaRepository<Terminator, Long> {

    fun findByOrderId(orderId: String): List<Terminator>
}
