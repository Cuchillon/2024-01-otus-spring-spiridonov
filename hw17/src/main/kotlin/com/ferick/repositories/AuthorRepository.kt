package com.ferick.repositories

import com.ferick.model.entities.Author
import org.springframework.data.jpa.repository.JpaRepository

interface AuthorRepository : JpaRepository<Author, Long>
