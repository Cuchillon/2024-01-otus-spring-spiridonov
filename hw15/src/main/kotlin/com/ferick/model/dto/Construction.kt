package com.ferick.model.dto

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.ferick.model.entities.Alloy
import com.ferick.model.entities.Covering
import com.ferick.model.entities.Skeleton

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonSubTypes(
    JsonSubTypes.Type(value = T800Construction::class),
    JsonSubTypes.Type(value = T1000Construction::class)
)
sealed interface Construction {
    val cpu: Cpu
}

data class T800Construction(
    override val cpu: Cpu,
    val skeleton: Skeleton,
    val covering: Covering
) : Construction

data class T1000Construction(
    override val cpu: Cpu,
    val alloy: Alloy
) : Construction
