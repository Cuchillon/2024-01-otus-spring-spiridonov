package com.ferick.exceptions

class QuestionReadException(
    message: String,
    ex: Throwable
) : RuntimeException(message, ex)
