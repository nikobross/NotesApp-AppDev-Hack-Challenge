// Repository.kt
package com.example.a6starter.repository

import com.example.a6starter.model.Note
import com.example.a6starter.model.User
import com.example.a6starter.network.ApiService
import com.example.a6starter.network.CreateNoteRequest
import com.example.a6starter.network.CreateUserRequest
import com.example.a6starter.network.UpdateNoteRequest
import retrofit2.Response

class Repository(private val apiService: ApiService) {

    // ----- User Operations -----

    suspend fun getAllUsers(): Response<List<User>> {
        return apiService.getAllUsers()
    }

    suspend fun createUser(username: String, password: String): Response<User> {
        val request = CreateUserRequest(username, password)
        return apiService.createUser(request)
    }

    suspend fun getUser(userId: Int): Response<User> {
        return apiService.getUser(userId)
    }

    suspend fun deleteUser(userId: Int): Response<User> {
        return apiService.deleteUser(userId)
    }

    suspend fun getNotesForUser(userId: Int): Response<List<Note>> {
        return apiService.getNotesForUser(userId)
    }

    // ----- Note Operations -----

    suspend fun getAllNotes(): Response<List<Note>> {
        return apiService.getAllNotes()
    }

    suspend fun getNote(noteId: Int): Response<Note> {
        return apiService.getNote(noteId)
    }

    suspend fun createNote(title: String, content: String, userId: Int): Response<Note> {
        val request = CreateNoteRequest(title, content, userId)
        return apiService.createNote(request)
    }

    suspend fun updateNote(noteId: Int, title: String, content: String): Response<Note> {
        val request = UpdateNoteRequest(title, content)
        return apiService.updateNote(noteId, request)
    }

    suspend fun deleteNote(noteId: Int): Response<Note> {
        return apiService.deleteNote(noteId)
    }
}
