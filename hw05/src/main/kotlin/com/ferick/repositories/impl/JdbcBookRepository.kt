package com.ferick.repositories.impl

import com.ferick.model.Author
import com.ferick.model.Book
import com.ferick.model.Genre
import com.ferick.repositories.BookRepository
import com.ferick.repositories.GenreRepository
import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class JdbcBookRepository(
    private val jdbc: NamedParameterJdbcOperations,
    private val genreRepository: GenreRepository
) : BookRepository {

    override fun findAll(): List<Book> {
        val genres = genreRepository.findAll()
        val relations = getAllGenreRelations()
        val books = getAllBooksWithoutGenres()
        mergeBooksInfo(books, genres, relations)
        return books
    }

    override fun findById(id: Long): Book? {
        TODO("Not yet implemented")
    }

    override fun save(book: Book): Book {
        return if (book.id == 0L) {
            insert(book)
        } else {
            update(book)
        }
    }

    override fun deleteById(id: Long) {
        TODO("Not yet implemented")
    }

    private fun getAllBooksWithoutGenres(): List<Book> {
        val sql = "SELECT b.id, b.title, b.author_id, a.full_name AS author_full_name " +
                "FROM books AS b " +
                "JOIN authors AS a ON b.author_id = a.id"
        return jdbc.query(sql, BookRowMapper())
    }

    private fun getAllGenreRelations(): List<BookGenreRelation> {
        return jdbc.queryForList(
            "SELECT book_id, genre_id FROM books_genres",
            emptyMap<String, Any>(),
            BookGenreRelation::class.java
        )
    }

    private fun mergeBooksInfo(
        booksWithoutGenres: List<Book>,
        genres: List<Genre>,
        relations: List<BookGenreRelation>
    ) {
        booksWithoutGenres.forEach { book ->
            val bookGenres = relations.asSequence()
                .filter { relation -> relation.bookId == book.id }
                .flatMap { relation -> genres.filter { genre -> relation.genreId == genre.id } }
            book.genres.addAll(bookGenres)
        }
    }

    private fun insert(book: Book): Book {
        TODO("Not yet implemented")
    }

    private fun update(book: Book): Book {
        TODO("Not yet implemented")
    }

    private class BookRowMapper : RowMapper<Book> {

        override fun mapRow(rs: ResultSet, rowNum: Int): Book {
            val id = rs.getLong("id")
            val title = rs.getString("title")
            val authorId = rs.getLong("author_id")
            val authorFullName = rs.getString("author_full_name")
            val author = Author(authorId, authorFullName)
            return Book(id, title, author)
        }
    }

    private class BookResultSetExtractor : ResultSetExtractor<Book> {

        override fun extractData(rs: ResultSet): Book? {
            return if (rs.next()) {
                val id = rs.getLong("id")
                val title = rs.getString("title")
                val authorId = rs.getLong("author_id")
                val authorFullName = rs.getString("author_full_name")
                val author = Author(authorId, authorFullName)
                Book(id, title, author)
            } else {
                null
            }
        }
    }

    private data class BookGenreRelation(val bookId: Long, val genreId: Long)
}
