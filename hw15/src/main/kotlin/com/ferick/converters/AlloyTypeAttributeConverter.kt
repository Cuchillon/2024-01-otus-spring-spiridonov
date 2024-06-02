package com.ferick.converters

import com.ferick.model.entities.AlloyType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class AlloyTypeAttributeConverter : AttributeConverter<AlloyType, String> {

    override fun convertToDatabaseColumn(alloyType: AlloyType): String {
        return alloyType.name
    }

    override fun convertToEntityAttribute(alloyType: String): AlloyType {
        return AlloyType.valueOf(alloyType)
    }
}
