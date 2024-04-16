package com.ferick.controllers

import com.ferick.model.dto.CreateBookRequest
import com.ferick.model.dto.UpdateBookRequest
import com.ferick.service.BookService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

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

    @GetMapping("/book")
    fun addBook(model: Model): String {
        model.addAttribute("book", CreateBookRequest())
        return "add_book"
    }

    @PostMapping("/book")
    fun addBook(@ModelAttribute("book") bookRequest: CreateBookRequest, model: Model): String {
        bookService.insert(bookRequest)
        return "redirect:/books"
    }

    @GetMapping("/book/{id}")
    fun updateBook(@PathVariable("id") id: Long, model: Model): String {
        model.addAttribute("book", UpdateBookRequest(id))
        return "update_book"
    }

    @PostMapping("/book/{id}")
    fun updateBook(
        @PathVariable("id") id: Long,
        @ModelAttribute("book") bookRequest: UpdateBookRequest,
        model: Model
    ): String {
        bookService.update(bookRequest)
        return "redirect:/books"
    }
}
