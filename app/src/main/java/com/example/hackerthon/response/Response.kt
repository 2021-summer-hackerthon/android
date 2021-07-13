package com.example.hackerthon.response

data class Response<T>(
    val data: T,
    val message: String,
    val status: Int
)