package com.ferick.model.entities

import com.ferick.converters.ConstructionAttributeConverter
import com.ferick.converters.TerminatorTypeAttributeConverter
import com.ferick.model.dto.Construction
import com.ferick.model.dto.TerminatorType
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "terminators")
class Terminator(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Convert(converter = TerminatorTypeAttributeConverter::class)
    @Column(name = "type")
    val type: TerminatorType,

    @Convert(converter = ConstructionAttributeConverter::class)
    @Column(name = "construction")
    val construction: Construction,

    @Column(name = "order_id")
    val orderId: String
)
