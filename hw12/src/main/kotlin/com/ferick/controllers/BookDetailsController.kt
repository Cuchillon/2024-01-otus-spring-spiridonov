package com.ferick.controllers

import com.ferick.service.BookCommentService
import com.ferick.service.BookService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class BookDetailsController(
    private val bookService: BookService,
    private val bookCommentService: BookCommentService
) {

    @GetMapping("/book-details/{id}")
    fun getBookDetails(@PathVariable("id") id: Long, model: Model): String {
        val book = bookService.findById(id)
        val bookComments = bookCommentService.findByBookId(id)
        model.addAttribute("book", book)
        model.addAttribute("bookComments", bookComments)
        return "book_details"
    }
}
