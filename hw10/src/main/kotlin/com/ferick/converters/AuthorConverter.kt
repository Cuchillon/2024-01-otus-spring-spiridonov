package com.ferick.converters

import com.ferick.model.dto.AuthorDto
import com.ferick.model.entities.Author
import org.springframework.stereotype.Component

@Component
class AuthorConverter {

    fun authorToDto(author: Author): AuthorDto = AuthorDto(
        id = author.id!!,
        fullName = author.fullName
    )
}
