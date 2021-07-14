package com.example.hackerthon.response

data class Comment(
    val anonymous: Int,
    val comment: String,
    val idx: Int,
    val star: Double,
    val user: User
)