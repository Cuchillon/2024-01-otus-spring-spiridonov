package com.ferick.converters

import com.ferick.model.dto.TerminatorDto
import com.ferick.model.entities.Terminator
import org.springframework.stereotype.Component

@Component
class TerminatorConverter {

    fun toDto(terminator: Terminator): TerminatorDto = TerminatorDto(
        type = terminator.type,
        construction = terminator.construction
    )
}
