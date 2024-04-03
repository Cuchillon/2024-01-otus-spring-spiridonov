package com.ferick.service.impl

import com.ferick.exceptions.EntityNotFoundException
import com.ferick.model.entities.BookComment
import com.ferick.repositories.BookCommentRepository
import com.ferick.repositories.BookRepository
import com.ferick.service.BookCommentService
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class BookCommentServiceImpl(
    private val bookCommentRepository: BookCommentRepository,
    private val bookRepository: BookRepository
) : BookCommentService {

    override fun findById(id: String): BookComment? {
        return bookCommentRepository.findById(id).getOrNull()
    }

    override fun findByBookId(bookId: String): List<BookComment> {
        if (bookRepository.findById(bookId).isEmpty) {
            throw EntityNotFoundException("Book with id $bookId not found")
        }
        return bookCommentRepository.findByBookId(bookId)
    }

    override fun insert(text: String, bookId: String): BookComment {
        return save(text, bookId)
    }

    override fun update(id: String, text: String, bookId: String): BookComment {
        return save(text, bookId, id)
    }

    override fun deleteById(id: String) {
        bookCommentRepository.deleteById(id)
    }

    private fun save(text: String, bookId: String, id: String? = null): BookComment {
        val book = bookRepository.findById(bookId).orElseThrow {
            EntityNotFoundException("Book with id $bookId not found")
        }
        val bookComment = BookComment(id, text, book)
        return bookCommentRepository.save(bookComment)
    }
}
