package com.ferick.converters

import com.ferick.model.Book
import org.springframework.stereotype.Component

@Component
class BookConverter(
    private val authorConverter: AuthorConverter,
    private val genreConverter: GenreConverter
) {

    fun bookToString(book: Book): String {
        val authorString = authorConverter.authorToString(book.author)
        val genresString = book.genres
            .map { genreConverter.genreToString(it) }
            .joinToString(", ") { "{$it}" }
        return "Id: ${book.id}, Title: ${book.title}, Author: {$authorString}, Genres: [$genresString]"
    }
}
