package com.ferick.controllers

import com.ferick.model.dto.BookDto
import com.ferick.model.dto.UpsertBookRequest
import com.ferick.service.BookService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class BookController(
    private val bookService: BookService
) {

    @GetMapping("/book")
    fun getAllBooks(): List<BookDto> {
        return bookService.findAll()
    }

    @GetMapping("/book/{id}")
    fun getBookById(@PathVariable("id") id: Long): BookDto {
        return bookService.findById(id)
    }

    @PostMapping("/book")
    fun createBook(
        @Valid @RequestBody bookRequest: UpsertBookRequest
    ): BookDto {
        return bookService.insert(bookRequest)
    }

    @PostMapping("/book/{id}")
    fun updateBook(
        @PathVariable("id") id: Long,
        @Valid @RequestBody bookRequest: UpsertBookRequest
    ): BookDto {
        return bookService.update(id, bookRequest)
    }

    @DeleteMapping("/book/{id}")
    fun deleteBook(
        @PathVariable("id") id: Long
    ) {
        bookService.deleteById(id)
    }
}
