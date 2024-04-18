package com.ferick.controllers

import com.ferick.model.dto.CreateBookCommentRequest
import com.ferick.model.dto.UpdateBookCommentRequest
import com.ferick.service.BookCommentService
import com.ferick.service.BookService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@Controller
class BookCommentController(
    private val bookService: BookService,
    private val bookCommentService: BookCommentService
) {

    @GetMapping("/comment-details/{id}")
    fun getCommentDetails(@PathVariable("id") id: Long, model: Model): String {
        val bookComment = bookCommentService.findById(id)
        model.addAttribute("bookComment", bookComment)
        return "comment_details"
    }

    @GetMapping("/comment/{bookId}")
    fun addComment(@PathVariable("bookId") bookId: Long, model: Model): String {
        val book = bookService.findById(bookId)
        model.addAttribute("book", book)
        model.addAttribute("comment", CreateBookCommentRequest(bookId))
        return "add_comment"
    }

    @PostMapping("/comment")
    fun addComment(
        @ModelAttribute("comment") bookCommentRequest: CreateBookCommentRequest,
        model: Model
    ): String {
        bookCommentService.insert(bookCommentRequest)
        return "redirect:/book-details/${bookCommentRequest.bookId}"
    }

    @GetMapping("/comment/{bookId}/{id}")
    fun updateComment(@PathVariable("bookId") bookId: Long, @PathVariable("id") id: Long, model: Model): String {
        val book = bookService.findById(bookId)
        model.addAttribute("book", book)
        model.addAttribute("comment", UpdateBookCommentRequest(id, bookId))
        return "update_comment"
    }

    @PostMapping("/comment/{id}")
    fun updateComment(
        @ModelAttribute("comment") bookCommentRequest: UpdateBookCommentRequest,
        model: Model
    ): String {
        bookCommentService.update(bookCommentRequest)
        return "redirect:/comment-details/${bookCommentRequest.id}"
    }

    @PostMapping("/comment/{id}/delete")
    fun deleteComment(
        @PathVariable("id") id: Long,
        model: Model
    ): String {
        bookCommentService.deleteById(id)
        return "redirect:/books"
    }
}
