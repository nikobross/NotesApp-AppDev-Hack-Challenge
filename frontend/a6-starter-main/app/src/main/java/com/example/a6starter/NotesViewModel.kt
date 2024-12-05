//package com.example.a6starter
//
//import androidx.lifecycle.ViewModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//
//class NotesViewModel : ViewModel() {
//
//    // Sample users (TEMPORARY)
//    private val _users = MutableStateFlow<List<User>>(
//        listOf(
//            User(1, "Alice"),
//            User(2, "Bob"),
//            User(3, "Charlie")
//        )
//    )
//    val users: StateFlow<List<User>> = _users
//
//    // Sample notes (TEMPORARY)
//    private val _notes = MutableStateFlow<List<Note>>(
//        listOf(
//            Note(1, 1, "Alice's first note"),
//            Note(2, 1, "Alice's second note"),
//            Note(3, 2, "Bob's first note")
//        )
//    )
//    val notes: StateFlow<List<Note>> = _notes
//
//
//    fun addNote(userId: Int, content: String) {
//        val newId = (_notes.value.maxOfOrNull { it.id } ?: 0) + 1
//        val newNote = Note(newId, userId, content)
//        _notes.value = _notes.value + newNote
//    }
//
//    fun getNotesForUser(userId: Int): List<Note> {
//        return _notes.value.filter { it.userId == userId }
//    }
//}

// NotesViewModel.kt
package com.example.a6starter

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NotesViewModel : ViewModel() {

    // Sample users
    private val _users = MutableStateFlow<List<User>>(
        listOf(
            User(1, "Alice"),
            User(2, "Bob"),
            User(3, "Charlie")
        )
    )
    val users: StateFlow<List<User>> = _users

    // Sample notes
    private val _notes = MutableStateFlow<List<Note>>(
        listOf(
            Note(1, 1, "Alice's first note"),
            Note(2, 1, "Alice's second note"),
            Note(3, 2, "Bob's first note")
        )
    )
    val notes: StateFlow<List<Note>> = _notes

    // Function to add a new note
    fun addNote(userId: Int, content: String) {
        val newId = (_notes.value.maxOfOrNull { it.id } ?: 0) + 1
        val newNote = Note(newId, userId, content)
        _notes.value = _notes.value + newNote
    }

    // Function to delete a note
    fun deleteNote(noteId: Int) {
        _notes.value = _notes.value.filter { it.id != noteId }
    }

    // Function to update a note
    fun updateNote(noteId: Int, newContent: String) {
        _notes.value = _notes.value.map { note ->
            if (note.id == noteId) note.copy(content = newContent) else note
        }
    }

    // Function to get notes for a specific user
    fun getNotesForUser(userId: Int): List<Note> {
        return _notes.value.filter { it.userId == userId }
    }
}
