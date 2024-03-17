package com.ferick.converters

import com.ferick.model.dto.BookDto
import com.ferick.model.entities.Book
import org.springframework.stereotype.Component

@Component
class BookConverter(
    private val authorConverter: AuthorConverter,
    private val genreConverter: GenreConverter,
    private val bookCommentConverter: BookCommentConverter
) {

    fun bookToString(bookDto: BookDto): String {
        val authorString = authorConverter.authorToString(bookDto.author)
        val genresString = bookDto.genres
            .map { genreConverter.genreToString(it) }
            .joinToString(", ") { "{$it}" }
        val commentString = bookDto.bookComments
            .map { bookCommentConverter.commentToString(it) }
            .joinToString(System.lineSeparator()) { it }
            .ifEmpty { "Still no comments..." }
        return """Id: ${bookDto.id}, Title: ${bookDto.title}, Author: {$authorString}, Genres: [$genresString]
            |Comments:
            |$commentString
        """.trimMargin()
    }

    fun bookToDto(book: Book): BookDto = BookDto(
        id = book.id!!,
        title = book.title,
        author = book.author,
        bookComments = book.bookComments,
        genres = book.genres
    )
}
