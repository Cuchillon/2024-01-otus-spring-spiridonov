package com.ferick.dao

import com.ferick.model.Question

interface QuestionDao {

    fun findAll(): List<Question>
}
