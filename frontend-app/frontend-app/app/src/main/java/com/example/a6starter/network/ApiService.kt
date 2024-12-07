package com.example.a6starter.network

import com.example.a6starter.model.Note
import com.example.a6starter.model.User
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // ----- User Endpoints -----

    @GET("api/users/")
    suspend fun getAllUsers(): Response<List<User>>

    @POST("api/users/")
    suspend fun createUser(@Body user: CreateUserRequest): Response<User>

    @GET("api/users/{user_id}/")
    suspend fun getUser(@Path("user_id") userId: Int): Response<User>

    @DELETE("api/users/{user_id}/")
    suspend fun deleteUser(@Path("user_id") userId: Int): Response<User>

    @GET("api/users/{user_id}/notes/")
    suspend fun getNotesForUser(@Path("user_id") userId: Int): Response<List<Note>>

    // ----- Note Endpoints -----

    @GET("api/notes/")
    suspend fun getAllNotes(): Response<List<Note>>

    @GET("api/notes/{note_id}/")
    suspend fun getNote(@Path("note_id") noteId: Int): Response<Note>

    @POST("api/notes/")
    suspend fun createNote(@Body note: CreateNoteRequest): Response<Note>

    @POST("api/notes/update/{note_id}/")
    suspend fun updateNote(
        @Path("note_id") noteId: Int,
        @Body note: UpdateNoteRequest
    ): Response<Note>

    @DELETE("api/notes/{note_id}/")
    suspend fun deleteNote(@Path("note_id") noteId: Int): Response<Note>
}
