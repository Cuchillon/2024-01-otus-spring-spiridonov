package com.ferick.converters

import com.ferick.model.entities.Genre
import org.springframework.stereotype.Component

@Component
class GenreConverter {

    fun genreToString(genre: Genre): String = "Id: ${genre.id}, Name: ${genre.name}"
}
