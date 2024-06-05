package com.ferick.service

import com.ferick.model.entities.Terminator
import com.ferick.model.dto.TerminatorOrderItem

interface AssemblingService {

    fun assemble(item: TerminatorOrderItem): Terminator
}
