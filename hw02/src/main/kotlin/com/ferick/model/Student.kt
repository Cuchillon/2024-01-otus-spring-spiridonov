package com.ferick.model

data class Student(
    val firstName: String,
    val lastName: String
) {
    fun getFullName() = "$firstName $lastName"
}
