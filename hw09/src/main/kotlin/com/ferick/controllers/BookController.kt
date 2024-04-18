package com.ferick.controllers

import com.ferick.model.dto.CreateBookRequest
import com.ferick.model.dto.UpdateBookRequest
import com.ferick.service.AuthorService
import com.ferick.service.BookService
import com.ferick.service.GenreService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
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
    fun addBook(@ModelAttribute("book") bookRequest: CreateBookRequest, model: Model): String {
        bookService.insert(bookRequest)
        return "redirect:/books"
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
        @ModelAttribute("book") bookRequest: UpdateBookRequest,
        model: Model
    ): String {
        bookService.update(bookRequest)
        return "redirect:/books"
    }

    @PostMapping("/book/{id}/delete")
    fun deleteBook(
        @PathVariable("id") id: Long,
        model: Model
    ): String {
        bookService.deleteById(id)
        return "redirect:/books"
    }
}
