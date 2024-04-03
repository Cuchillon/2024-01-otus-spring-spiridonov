package com.ferick.changelogs

import com.ferick.model.entities.Author
import com.ferick.model.entities.Book
import com.ferick.model.entities.BookComment
import com.ferick.model.entities.Genre
import com.ferick.repositories.AuthorRepository
import com.ferick.repositories.BookCommentRepository
import com.ferick.repositories.BookRepository
import com.ferick.repositories.GenreRepository
import com.github.cloudyrock.mongock.ChangeLog
import com.github.cloudyrock.mongock.ChangeSet
import com.mongodb.client.MongoDatabase

@ChangeLog(order = "001")
class MongoDataChangeLog {

    private val authors = mutableListOf<Author>()
    private val genres = mutableListOf<Genre>()
    private val books = mutableListOf<Book>()

    @ChangeSet(order = "000", id = "dropDB", author = "ferick", runAlways = true)
    fun dropDB(dataBase: MongoDatabase) {
        dataBase.drop()
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "ferick", runAlways = true)
    fun initAuthors(authorRepository: AuthorRepository) {
        authorNames.forEach { authorName ->
            val author = authorRepository.save(Author(fullName = authorName))
            authors.add(author)
        }
    }

    @ChangeSet(order = "002", id = "initGenres", author = "ferick", runAlways = true)
    fun initGenres(genreRepository: GenreRepository) {
        genreNames.forEach { genreName ->
            val genre = genreRepository.save(Genre(name = genreName))
            genres.add(genre)
        }
    }

    @ChangeSet(order = "003", id = "initBooks", author = "ferick", runAlways = true)
    fun initBooks(bookRepository: BookRepository) {
        bookRelationList.forEach { bookRelations ->
            val book = Book(
                title = bookRelations.title,
                author = authors[bookRelations.authorIndex],
                genres = bookRelations.genreIndices.map { genres[it] }
            )
            val savedBook = bookRepository.save(book)
            books.add(savedBook)
        }
    }

    @ChangeSet(order = "004", id = "initBookComments", author = "ferick", runAlways = true)
    fun initBookComments(bookCommentRepository: BookCommentRepository) {
        bookCommentRelationList.forEach { commentRelations ->
            val comment = BookComment(
                text = commentRelations.first,
                book = books[commentRelations.second]
            )
            bookCommentRepository.save(comment)
        }
    }

    companion object {
        data class BookRelations(val title: String, val authorIndex: Int, val genreIndices: Set<Int>)

        val authorNames = listOf(
            "Conan Quasi-Doyle",
            "Emmanuelle Quasi-Arsan",
            "Bram Quasi-Stoker"
        )

        private val genreNames = listOf(
            "Science fiction",
            "Detective fiction",
            "Thriller",
            "Horror",
            "Erotic",
            "Porn"
        )

        private val bookRelationList = listOf(
            BookRelations("Sherlock Holmes in Space", 0, setOf(0, 1)),
            BookRelations("Emmanuelle and the Night Chase", 1, setOf(2, 4)),
            BookRelations("Dracula and the Big Dildo", 2, setOf(3, 5))
        )

        private val bookCommentRelationList = listOf(
            "Awesome book, like it" to 0,
            "Holmes in Space is something new" to 0,
            "Emmanuelle is very sexy as always" to 1,
            "Waiting for a new book with Emmanuelle and Emmanuel Macron" to 1,
            "The dildo is OK" to 2,
            "It is a good fiction enough to fall asleep" to 2,
        )
    }
}
