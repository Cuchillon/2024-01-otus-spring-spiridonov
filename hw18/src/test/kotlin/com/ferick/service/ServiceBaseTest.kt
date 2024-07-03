package com.ferick.service

import com.ferick.repositories.AuthorRepository
import com.ferick.repositories.BookCommentRepository
import com.ferick.repositories.BookRepository
import com.ferick.repositories.GenreRepository
import com.ninjasquad.springmockk.MockkBean
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
abstract class ServiceBaseTest {

    @MockkBean
    protected lateinit var authorRepository: AuthorRepository

    @MockkBean
    protected lateinit var genreRepository: GenreRepository

    @MockkBean
    protected lateinit var bookRepository: BookRepository

    @MockkBean
    protected lateinit var bookCommentRepository: BookCommentRepository
}
