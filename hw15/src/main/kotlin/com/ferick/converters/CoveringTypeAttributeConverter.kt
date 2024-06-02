package com.ferick.converters

import com.ferick.model.entities.CoveringType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class CoveringTypeAttributeConverter : AttributeConverter<CoveringType, String> {

    override fun convertToDatabaseColumn(coveringType: CoveringType): String {
        return coveringType.name
    }

    override fun convertToEntityAttribute(coveringType: String): CoveringType {
        return CoveringType.valueOf(coveringType)
    }
}
