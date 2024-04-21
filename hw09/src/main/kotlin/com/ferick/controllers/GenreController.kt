package com.ferick.controllers

import com.ferick.service.GenreService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class GenreController(
    private val genreService: GenreService
) {

    @GetMapping("/genres")
    fun getAllGenres(model: Model): String {
        val genres = genreService.findAll()
        model.addAttribute("genres", genres)
        return "genres"
    }
}
