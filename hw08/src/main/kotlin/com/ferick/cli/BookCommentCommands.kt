package com.ferick.cli

import com.ferick.converters.BookCommentConverter
import com.ferick.service.BookCommentService
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption

@ShellComponent
class BookCommentCommands(
    private val bookCommentService: BookCommentService,
    private val bookCommentConverter: BookCommentConverter
) {

    @ShellMethod("Find comments by book id", key = ["find-comments"])
    fun findCommentsByBookId(
        @ShellOption("--book-id", "-b") bookId: String
    ): String {
        val comments = bookCommentService.findByBookId(bookId)
        val bookTitle = comments.first().book.title
        return comments
            .map { bookCommentConverter.commentToString(it) }
            .joinToString(
                separator = ",${System.lineSeparator()}",
                prefix = "Book title: $bookTitle${System.lineSeparator()}"
            ) { it }
    }

    @ShellMethod("Find book comment by id", key = ["find-comment"])
    fun findCommentById(
        @ShellOption("--id", "-i") id: String
    ): String {
        return bookCommentService.findById(id)?.let {
            bookCommentConverter.commentToString(it)
        } ?: "Book comment with id $id not found"
    }

    @ShellMethod("Insert book comment", key = ["insert-comment"])
    fun insertBookComment(
        @ShellOption("--text", "-t") text: String,
        @ShellOption("--book-id", "-b") bookId: String
    ): String {
        val comment = bookCommentService.insert(text, bookId)
        return bookCommentConverter.commentToString(comment)
    }

    @ShellMethod("Update book comment", key = ["update-comment"])
    fun updateBookComment(
        @ShellOption("--id", "-i") id: String,
        @ShellOption("--text", "-t") text: String,
        @ShellOption("--book-id", "-b") bookId: String
    ): String {
        val comment = bookCommentService.update(id, text, bookId)
        return bookCommentConverter.commentToString(comment)
    }

    @ShellMethod("Delete book comment by id", key = ["delete-comment"])
    fun deleteBookComment(
        @ShellOption("--id", "-i") id: String
    ) {
        bookCommentService.deleteById(id)
    }
}
