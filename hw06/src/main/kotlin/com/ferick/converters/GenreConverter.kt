package com.ferick.converters

import com.ferick.model.dto.GenreDto
import com.ferick.model.entities.Genre
import org.springframework.stereotype.Component

@Component
class GenreConverter {

    fun genreToString(genreDto: GenreDto): String = "Id: ${genreDto.id}, Name: ${genreDto.name}"

    fun genreToDto(genre: Genre): GenreDto = GenreDto(
        id = genre.id!!,
        name = genre.name
    )
}
