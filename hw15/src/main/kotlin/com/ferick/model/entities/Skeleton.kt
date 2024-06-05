package com.ferick.model.entities

import com.ferick.converters.SkeletonTypeAttributeConverter
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "skeletons")
class Skeleton(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Convert(converter = SkeletonTypeAttributeConverter::class)
    @Column(name = "type")
    val type: SkeletonType = SkeletonType.TITAN,

    @Column(name = "uuid")
    val uuid: String = UUID.randomUUID().toString()
)

enum class SkeletonType {
    TITAN
}
