package com.ferick.service.impl

import com.ferick.exceptions.EntityNotFoundException
import com.ferick.model.entities.BookComment
import com.ferick.repositories.BookCommentRepository
import com.ferick.repositories.BookRepository
import com.ferick.service.BookCommentService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
class BookCommentServiceImpl(
    private val bookCommentRepository: BookCommentRepository,
    private val bookRepository: BookRepository
) : BookCommentService {

    @Transactional(readOnly = true)
    override fun findById(id: Long): BookComment? {
        return bookCommentRepository.findById(id).getOrNull()
    }

    @Transactional(readOnly = true)
    override fun findByBookId(bookId: Long): List<BookComment> {
        if (bookRepository.findById(bookId).isEmpty) {
            throw EntityNotFoundException("Book with id $bookId not found")
        }
        return bookCommentRepository.findByBookId(bookId)
    }

    @Transactional
    override fun insert(text: String, bookId: Long): BookComment {
        return save(text, bookId)
    }

    @Transactional
    override fun update(id: Long, text: String, bookId: Long): BookComment {
        return save(text, bookId, id)
    }

    @Transactional
    override fun deleteById(id: Long) {
        bookCommentRepository.deleteById(id)
    }

    private fun save(text: String, bookId: Long, id: Long? = null): BookComment {
        val book = bookRepository.findById(bookId).orElseThrow {
            EntityNotFoundException("Book with id $bookId not found")
        }
        val bookComment = BookComment(id, text, book)
        return bookCommentRepository.save(bookComment)
    }
}
