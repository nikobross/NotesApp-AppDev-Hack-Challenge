package com.example.a6starter.network

data class CreateNoteRequest(
    val title: String,
    val content: String,
    val user_id: Int
)