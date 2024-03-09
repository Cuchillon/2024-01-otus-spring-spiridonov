package com.ferick.repositories.impl

import com.ferick.exceptions.EntityNotFoundException
import com.ferick.model.Author
import com.ferick.model.Book
import com.ferick.model.Genre
import com.ferick.repositories.BookRepository
import com.ferick.repositories.GenreRepository
import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.support.GeneratedKeyHolder
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
        val sql = "SELECT b.id, b.title, b.author_id, a.full_name AS author_full_name " +
                "FROM books AS b " +
                "JOIN authors AS a " +
                "ON b.author_id = a.id " +
                "WHERE id = :id"
        val params = MapSqlParameterSource()
            .addValue("id", id)
        return jdbc.query(sql, params, BookResultSetExtractor())
    }

    override fun save(book: Book): Book {
        return if (book.id == 0L) {
            insert(book)
        } else {
            update(book)
        }
    }

    override fun deleteById(id: Long) {
        val params = MapSqlParameterSource()
            .addValue("id", id)
        jdbc.update("DELETE FROM books WHERE id = :id", params)
    }

    private fun getAllBooksWithoutGenres(): List<Book> {
        val sql = "SELECT b.id, b.title, b.author_id, a.full_name AS author_full_name " +
                "FROM books AS b " +
                "JOIN authors AS a " +
                "ON b.author_id = a.id"
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
        val keyHolder = GeneratedKeyHolder()
        val params = MapSqlParameterSource()
            .addValue("title", book.title)
            .addValue("author_id", book.author.id)
        jdbc.update(
            "INSERT INTO books (title, author_id) VALUES (:title, :author_id)",
            params,
            keyHolder,
            arrayOf("id")
        )
        book.id = keyHolder.getKeyAs(Long::class.java)!!
        batchInsertGenresRelationsFor(book)
        return book
    }

    private fun update(book: Book): Book {
        val params = MapSqlParameterSource()
            .addValue("id", book.id)
            .addValue("title", book.title)
            .addValue("author_id", book.author.id)
        val affectedRows = jdbc.update(
            "UPDATE books SET title = :title, author_id = :author_id WHERE id = :id",
            params
        )
        if (affectedRows == 0) {
            throw EntityNotFoundException("Book with id ${book.id} not found")
        }
        removeGenresRelationsFor(book)
        batchInsertGenresRelationsFor(book)
        return book
    }

    private fun batchInsertGenresRelationsFor(book: Book) {
        val parameterSources = book.genres.map { genre ->
            MapSqlParameterSource()
                .addValue("book_id", book.id)
                .addValue("genre_id", genre.id)
        }.toTypedArray()
        jdbc.batchUpdate(
            "INSERT INTO books_genres (book_id, genre_id) VALUES (:book_id, :genre_id)",
            parameterSources
        )
    }

    private fun removeGenresRelationsFor(book: Book) {
        val params = MapSqlParameterSource()
            .addValue("book_id", book.id)
        jdbc.update("DELETE FROM books_genres WHERE book_id = :book_id", params)
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
