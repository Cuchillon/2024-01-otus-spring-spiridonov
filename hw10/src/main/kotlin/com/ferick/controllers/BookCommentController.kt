package com.ferick.controllers

import com.ferick.model.dto.UpsertBookCommentRequest
import com.ferick.model.entities.BookComment
import com.ferick.service.BookCommentService
import com.ferick.service.BookService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class BookCommentController(
    private val bookService: BookService,
    private val bookCommentService: BookCommentService
) {

    @GetMapping("/book-comment/book/{bookId}")
    fun getBookCommentsByBookId(
        @PathVariable("bookId") bookId: Long
    ): List<BookComment> {
        return bookCommentService.findByBookId(bookId)
    }

    @GetMapping("/book-comment/{id}")
    fun getBookCommentById(
        @PathVariable("id") id: Long
    ): BookComment {
        return bookCommentService.findById(id)
    }

    @PostMapping("/book-comment")
    fun createComment(
        @Valid @RequestBody bookCommentRequest: UpsertBookCommentRequest
    ): BookComment {
        return bookCommentService.insert(bookCommentRequest)
    }

    @PostMapping("/book-comment/{id}")
    fun updateComment(
        @PathVariable("id") id: Long,
        @Valid @RequestBody bookCommentRequest: UpsertBookCommentRequest
    ): BookComment {
        return bookCommentService.update(id, bookCommentRequest)
    }

    @PostMapping("/book-comment/{id}")
    fun deleteComment(
        @PathVariable("id") id: Long
    ) {
        bookCommentService.deleteById(id)
    }
}
