package com.ferick.converters

import com.ferick.model.Author
import org.springframework.stereotype.Component

@Component
class AuthorConverter {

    fun authorToString(author: Author): String = "Id: ${author.id}, Full name: ${author.fullName}"
}
