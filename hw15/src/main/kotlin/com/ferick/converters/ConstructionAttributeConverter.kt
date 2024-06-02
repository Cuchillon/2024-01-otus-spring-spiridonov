package com.ferick.converters

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ferick.model.dto.Construction
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class ConstructionAttributeConverter : AttributeConverter<Construction, String> {

    override fun convertToDatabaseColumn(construction: Construction): String {
        return jacksonObjectMapper().writeValueAsString(construction)
    }

    override fun convertToEntityAttribute(construction: String): Construction {
        return jacksonObjectMapper().readValue(construction, Construction::class.java)
    }
}
