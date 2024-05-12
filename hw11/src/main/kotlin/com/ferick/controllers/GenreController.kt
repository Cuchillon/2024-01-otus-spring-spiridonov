package com.ferick.controllers

import com.ferick.model.dto.GenreDto
import com.ferick.service.GenreService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
class GenreController(
    private val genreService: GenreService
) {

    @GetMapping("/genre")
    fun getAllGenres(): Flux<GenreDto> {
        return genreService.findAll()
    }
}
