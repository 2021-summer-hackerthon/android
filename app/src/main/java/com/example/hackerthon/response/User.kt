package com.example.hackerthon.response

data class User(
    val createdAt: String,
    val grade: Int,
    val name: String,
    val number: Int,
    val profileImage: String,
    val room: Int,
    val updatedAt: String,
    val userId: String
)