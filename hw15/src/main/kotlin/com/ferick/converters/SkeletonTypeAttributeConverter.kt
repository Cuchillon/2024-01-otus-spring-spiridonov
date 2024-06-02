package com.ferick.converters

import com.ferick.model.entities.SkeletonType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class SkeletonTypeAttributeConverter : AttributeConverter<SkeletonType, String> {

    override fun convertToDatabaseColumn(skeletonType: SkeletonType): String {
        return skeletonType.name
    }

    override fun convertToEntityAttribute(skeletonType: String): SkeletonType {
        return SkeletonType.valueOf(skeletonType)
    }
}
