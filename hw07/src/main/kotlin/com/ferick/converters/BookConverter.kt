package com.ferick.converters

import com.ferick.model.dto.BookDto
import com.ferick.model.entities.Book
import org.springframework.stereotype.Component

@Component
class BookConverter(
    private val authorConverter: AuthorConverter,
    private val genreConverter: GenreConverter
) {

    fun bookToString(bookDto: BookDto): String {
        val authorString = authorConverter.authorToString(bookDto.author)
        val genresString = bookDto.genres
            .map { genreConverter.genreToString(it) }
            .joinToString(", ") { "{$it}" }
        return "Id: ${bookDto.id}, Title: ${bookDto.title}, Author: {$authorString}, Genres: [$genresString]"
    }

    fun bookToDto(book: Book): BookDto = BookDto(
        id = book.id!!,
        title = book.title,
        author = book.author,
        genres = book.genres.map { genreConverter.genreToDto(it) }
    )
}
