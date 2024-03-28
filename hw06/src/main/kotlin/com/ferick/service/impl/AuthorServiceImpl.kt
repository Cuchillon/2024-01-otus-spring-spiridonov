package com.ferick.service.impl

import com.ferick.model.entities.Author
import com.ferick.repositories.AuthorRepository
import com.ferick.service.AuthorService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthorServiceImpl(
    private val authorRepository: AuthorRepository
) : AuthorService {

    @Transactional(readOnly = true)
    override fun findAll(): List<Author> {
        return authorRepository.findAll()
    }
}
