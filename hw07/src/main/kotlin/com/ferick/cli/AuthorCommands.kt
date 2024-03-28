package com.ferick.cli

import com.ferick.converters.AuthorConverter
import com.ferick.service.AuthorService
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class AuthorCommands(
    private val authorService: AuthorService,
    private val authorConverter: AuthorConverter
) {

    @ShellMethod("Find all authors", key = ["authors"])
    fun findAllAuthors(): String {
        return authorService.findAll()
            .map { authorConverter.authorToString(it) }
            .joinToString(",${System.lineSeparator()}") { it }
    }
}
