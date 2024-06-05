package com.ferick.model.entities

import com.ferick.converters.CoveringTypeAttributeConverter
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "covering")
class Covering(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Convert(converter = CoveringTypeAttributeConverter::class)
    @Column(name = "type")
    val type: CoveringType = CoveringType.ORGANIC,

    @Column(name = "uuid")
    val uuid: String = UUID.randomUUID().toString()
)

enum class CoveringType {
    ORGANIC
}
