package com.ferick.controllers

import com.ferick.model.dto.AuthorDto
import com.ferick.service.AuthorService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthorController(
    private val authorService: AuthorService
) {

    @GetMapping("/author")
    fun getAllAuthors(): List<AuthorDto> {
        return authorService.findAll()
    }
}
