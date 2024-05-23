package com.ferick.controllers

import com.ferick.service.AuthorService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AuthorController(
    private val authorService: AuthorService
) {

    @GetMapping("/authors")
    fun getAllAuthors(model: Model): String {
        val authors = authorService.findAll()
        model.addAttribute("authors", authors)
        return "authors"
    }
}
