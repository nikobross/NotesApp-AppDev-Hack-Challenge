package com.example.a6starter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a6starter.model.Note
import com.example.a6starter.model.User
import com.example.a6starter.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

class NotesViewModel(private val repository: Repository) : ViewModel() {

    private val _usersState = MutableStateFlow<UiState<List<User>>>(UiState.Loading)
    val usersState: StateFlow<UiState<List<User>>> = _usersState

    private val _notesState = MutableStateFlow<UiState<List<Note>>>(UiState.Loading)
    val notesState: StateFlow<UiState<List<Note>>> = _notesState

    private var currentUserId: Int? = null

    init {
        fetchAllUsers()
    }


    fun fetchAllUsers() {
        viewModelScope.launch {
            _usersState.value = UiState.Loading
            try {
                val response = repository.getAllUsers()
                if (response.isSuccessful) {
                    _usersState.value = UiState.Success(response.body() ?: emptyList())
                } else {
                    _usersState.value = UiState.Error("Failed to load users: ${response.message()}")
                }
            } catch (e: Exception) {
                _usersState.value = UiState.Error("An error occurred: ${e.localizedMessage}")
            }
        }
    }

    fun createUser(username: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.createUser(username, password)
                if (response.isSuccessful) {
                    fetchAllUsers()
                }
            } catch (e: Exception) {

            }
        }
    }

    fun deleteUser(userId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.deleteUser(userId)
                if (response.isSuccessful) {
                    fetchAllUsers()
                }
            } catch (e: Exception) {

            }
        }
    }


    fun fetchNotesForUser(userId: Int) {
        viewModelScope.launch {
            _notesState.value = UiState.Loading
            currentUserId = userId
            try {
                val response = repository.getNotesForUser(userId)
                if (response.isSuccessful) {
                    _notesState.value = UiState.Success(response.body() ?: emptyList())
                } else {
                    _notesState.value = UiState.Error("Failed to load notes: ${response.message()}")
                }
            } catch (e: Exception) {
                _notesState.value = UiState.Error("An error occurred: ${e.localizedMessage}")
            }
        }
    }

    fun createNote(title: String, content: String, userId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.createNote(title, content, userId)
                if (response.isSuccessful) {
                    fetchNotesForUser(userId)
                }
            } catch (e: Exception) {
            }
        }
    }

    fun updateNote(noteId: Int, title: String, content: String) {
        viewModelScope.launch {
            try {
                val response = repository.updateNote(noteId, title, content)
                if (response.isSuccessful) {
                    currentUserId?.let { fetchNotesForUser(it) }
                }
            } catch (e: Exception) {
            }
        }
    }

    fun deleteNote(noteId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.deleteNote(noteId)
                if (response.isSuccessful) {
                    currentUserId?.let { fetchNotesForUser(it) }
                }
            } catch (e: Exception) {

            }
        }
    }
}
