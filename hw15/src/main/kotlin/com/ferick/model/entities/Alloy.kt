package com.ferick.model.entities

import com.ferick.converters.AlloyTypeAttributeConverter
import jakarta.persistence.Column
import jakarta.persistence.Convert
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

    @Convert(converter = AlloyTypeAttributeConverter::class)
    @Column(name = "type")
    val type: AlloyType = AlloyType.LIQUID_METAL,

    @Column(name = "uuid")
    val uuid: String = UUID.randomUUID().toString()
)

enum class AlloyType {
    LIQUID_METAL
}
