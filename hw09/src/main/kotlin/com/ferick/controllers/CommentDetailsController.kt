package com.ferick.controllers

import com.ferick.service.BookCommentService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class CommentDetailsController(
    private val bookCommentService: BookCommentService
) {

    @GetMapping("/comment-details/{id}")
    fun getCommentDetails(@PathVariable("id") id: Long, model: Model): String {
        val bookComment = bookCommentService.findById(id)
        model.addAttribute("bookComment", bookComment)
        return "comment_details"
    }
}
