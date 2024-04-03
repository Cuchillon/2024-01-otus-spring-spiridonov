package com.ferick.cli

import com.ferick.converters.BookConverter
import com.ferick.service.BookService
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption

@ShellComponent
class BookCommands(
    private val bookService: BookService,
    private val bookConverter: BookConverter
) {

    @ShellMethod("Find all books", key = ["books"])
    fun findAllBooks(): String {
        return bookService.findAll()
            .map { bookConverter.bookToString(it) }
            .joinToString(",${System.lineSeparator()}") { it }
    }

    @ShellMethod("Find book by id", key = ["find-book"])
    fun findBookById(
        @ShellOption("--id", "-i") id: String
    ): String {
        return bookService.findById(id)?.let {
            bookConverter.bookToString(it)
        } ?: "Book with id $id not found"
    }

    @ShellMethod("Insert book", key = ["insert-book"])
    fun insertBook(
        @ShellOption("--title", "-t") title: String,
        @ShellOption("--author-id", "-a") authorId: String,
        @ShellOption("--genres-ids", "-g") genresIds: Set<String>
    ): String {
        val savedBook = bookService.insert(title, authorId, genresIds)
        return bookConverter.bookToString(savedBook)
    }

    @ShellMethod("Update book", key = ["update-book"])
    fun updateBook(
        @ShellOption("--id", "-i") id: String,
        @ShellOption("--title", "-t") title: String,
        @ShellOption("--author-id", "-a") authorId: String,
        @ShellOption("--genres-ids", "-g") genresIds: Set<String>
    ): String {
        val savedBook = bookService.update(id, title, authorId, genresIds)
        return bookConverter.bookToString(savedBook)
    }

    @ShellMethod("Delete book by id", key = ["delete-book"])
    fun deleteBook(
        @ShellOption("--id", "-i") id: String
    ) {
        bookService.deleteById(id)
    }
}
