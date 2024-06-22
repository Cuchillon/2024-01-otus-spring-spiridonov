package com.ferick.converters

import com.ferick.model.dto.GenreDto
import com.ferick.model.entities.Genre
import org.springframework.stereotype.Component

@Component
class GenreConverter {

    fun genreToDto(genre: Genre): GenreDto = GenreDto(
        id = genre.id!!,
        name = genre.name
    )
}
