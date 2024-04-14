package com.ferick.service.impl

import com.ferick.model.entities.Author
import com.ferick.repositories.AuthorRepository
import com.ferick.service.AuthorService
import org.springframework.stereotype.Service

@Service
class AuthorServiceImpl(
    private val authorRepository: AuthorRepository
) : AuthorService {

    override fun findAll(): List<Author> {
        return authorRepository.findAll()
    }
}
