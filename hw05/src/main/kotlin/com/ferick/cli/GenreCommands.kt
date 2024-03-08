package com.ferick.cli

import com.ferick.converters.GenreConverter
import com.ferick.service.GenreService
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class GenreCommands(
    private val genreService: GenreService,
    private val genreConverter: GenreConverter
) {

    @ShellMethod("Find all genres", key = ["genres"])
    fun findAllGenres(): String {
        return genreService.findAll()
            .map { genreConverter.genreToString(it) }
            .joinToString(",${System.lineSeparator()}") { it }
    }
}
