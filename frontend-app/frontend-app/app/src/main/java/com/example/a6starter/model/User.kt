package com.example.a6starter.model

data class User(
    val id: Int,
    val username: String,
    val password: String,
    val notes: List<Note>
)
