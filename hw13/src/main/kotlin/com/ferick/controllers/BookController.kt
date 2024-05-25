package com.ferick.controllers

import com.ferick.model.dto.CreateBookRequest
import com.ferick.model.dto.UpdateBookRequest
import com.ferick.service.AuthorService
import com.ferick.service.BookService
import com.ferick.service.GenreService
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@Controller
class BookController(
    private val bookService: BookService,
    private val authorService: AuthorService,
    private val genreService: GenreService
) {

    @GetMapping("/books")
    fun getAllBooks(model: Model): String {
        val books = bookService.findAll()
        model.addAttribute("books", books)
        return "books"
    }

    @GetMapping("/book")
    fun addBook(model: Model): String {
        val authors = authorService.findAll()
        val genres = genreService.findAll()
        model.addAttribute("genres", genres)
        model.addAttribute("authors", authors)
        model.addAttribute("book", CreateBookRequest())
        return "add_book"
    }

    @PostMapping("/book")
    fun addBook(
        @Valid @ModelAttribute("book") bookRequest: CreateBookRequest,
        bindingResult: BindingResult,
        model: Model
    ): String = checkErrors(bindingResult, model, "add_book") {
        bookService.insert(bookRequest)
        "redirect:/books"
    }

    @GetMapping("/book/{id}")
    fun updateBook(@PathVariable("id") id: Long, model: Model): String {
        val authors = authorService.findAll()
        val genres = genreService.findAll()
        model.addAttribute("genres", genres)
        model.addAttribute("authors", authors)
        model.addAttribute("book", UpdateBookRequest(id))
        return "update_book"
    }

    @PostMapping("/book/{id}")
    fun updateBook(
        @PathVariable("id") id: Long,
        @Valid @ModelAttribute("book") bookRequest: UpdateBookRequest,
        bindingResult: BindingResult,
        model: Model
    ): String = checkErrors(bindingResult, model, "update_book") {
        bookService.update(bookRequest)
        "redirect:/book-details/${id}"
    }

    @PostMapping("/book/{id}/delete")
    fun deleteBook(
        @PathVariable("id") id: Long,
        model: Model
    ): String {
        bookService.deleteById(id)
        return "redirect:/books"
    }

    private fun checkErrors(
        bindingResult: BindingResult,
        model: Model,
        templateName: String,
        block: () -> String
    ): String =
        if (bindingResult.hasErrors()) {
            val authors = authorService.findAll()
            val genres = genreService.findAll()
            model.addAttribute("genres", genres)
            model.addAttribute("authors", authors)
            templateName
        } else {
            block.invoke()
        }
}
