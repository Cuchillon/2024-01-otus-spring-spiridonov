package com.ferick.service

import com.ferick.model.Student

interface StudentService {

    fun determineCurrentStudent(): Student
}
