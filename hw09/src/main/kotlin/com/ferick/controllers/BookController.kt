package com.ferick.controllers

import com.ferick.service.BookService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class BookController(
    private val bookService: BookService
) {

    @GetMapping("/books")
    fun getAllBooks(model: Model): String {
        val books = bookService.findAll()
        model.addAttribute("books", books)
        return "books"
    }
}
