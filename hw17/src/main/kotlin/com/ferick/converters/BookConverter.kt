package com.ferick.converters

import com.ferick.model.dto.BookDto
import com.ferick.model.entities.Book
import org.springframework.stereotype.Component

@Component
class BookConverter(
    private val authorConverter: AuthorConverter,
    private val genreConverter: GenreConverter
) {

    fun bookToDto(book: Book): BookDto = BookDto(
        id = book.id!!,
        title = book.title,
        author = authorConverter.authorToDto(book.author),
        genres = book.genres.map { genreConverter.genreToDto(it) }
    )
}
