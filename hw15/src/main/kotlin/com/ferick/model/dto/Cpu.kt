package com.ferick.model.dto

import java.util.UUID

data class Cpu(
    val uuid: UUID = UUID.randomUUID(),
    val type: CpuType = CpuType.ARTIFICIAL_NEURAL_NETWORK
)
