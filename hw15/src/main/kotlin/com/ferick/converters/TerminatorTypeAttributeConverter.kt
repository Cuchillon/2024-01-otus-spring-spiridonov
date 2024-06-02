package com.ferick.converters

import com.ferick.model.dto.TerminatorType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class TerminatorTypeAttributeConverter : AttributeConverter<TerminatorType, String> {

    override fun convertToDatabaseColumn(terminatorType: TerminatorType): String {
        return terminatorType.name
    }

    override fun convertToEntityAttribute(terminatorType: String): TerminatorType {
        return TerminatorType.valueOf(terminatorType)
    }
}
