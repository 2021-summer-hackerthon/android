package com.example.hackerthon.response

data class Response(
    val data: List<Data>,
    val message: String,
    val status: Int
)