package com.ferick.model.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "alloys")
class Alloy(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "type")
    val type: AlloyType = AlloyType.LIQUID_METAL,

    @Column(name = "uuid")
    val uuid: String = UUID.randomUUID().toString()
)

enum class AlloyType {
    LIQUID_METAL
}
