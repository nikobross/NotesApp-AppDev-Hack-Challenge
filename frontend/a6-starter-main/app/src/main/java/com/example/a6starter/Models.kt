package com.example.a6starter

data class User(
    val id: Int,
    val name: String
)

data class Note(
    val id: Int,
    val userId: Int,
    val content: String
)