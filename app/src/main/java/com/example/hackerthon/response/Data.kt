package com.example.hackerthon.response

data class Data(
    val anonymous: Int,
    val comment: List<Comment>,
    val createdAt: String,
    val discript: String,
    val idx: Int,
    val image: String,
    val name: String,
    val phone: String,
    val star: Any,
    val xPosition: String,
    val yPosition: String
)