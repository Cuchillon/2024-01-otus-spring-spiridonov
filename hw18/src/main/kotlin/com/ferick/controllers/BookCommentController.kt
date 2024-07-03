package com.ferick.controllers

import com.ferick.model.dto.BookCommentDto
import com.ferick.model.dto.UpsertBookCommentRequest
import com.ferick.service.BookCommentService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class BookCommentController(
    private val bookCommentService: BookCommentService
) {

    @GetMapping("/book-comment/book/{bookId}")
    fun getBookCommentsByBookId(
        @PathVariable("bookId") bookId: Long
    ): List<BookCommentDto> {
        return bookCommentService.findByBookId(bookId)
    }

    @GetMapping("/book-comment/{id}")
    fun getBookCommentById(
        @PathVariable("id") id: Long
    ): BookCommentDto {
        return bookCommentService.findById(id)
    }

    @PostMapping("/book-comment")
    fun createComment(
        @Valid @RequestBody bookCommentRequest: UpsertBookCommentRequest
    ): BookCommentDto {
        return bookCommentService.insert(bookCommentRequest)
    }

    @PutMapping("/book-comment/{id}")
    fun updateComment(
        @PathVariable("id") id: Long,
        @Valid @RequestBody bookCommentRequest: UpsertBookCommentRequest
    ): BookCommentDto {
        return bookCommentService.update(id, bookCommentRequest)
    }

    @DeleteMapping("/book-comment/{id}")
    fun deleteComment(
        @PathVariable("id") id: Long
    ) {
        bookCommentService.deleteById(id)
    }
}
