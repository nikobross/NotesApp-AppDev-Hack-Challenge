package com.example.a6starter.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.a6starter.model.Note
import com.example.a6starter.viewmodel.NotesViewModel
import com.example.a6starter.ui.components.EditNoteDialog
import com.example.a6starter.ui.components.DeleteConfirmationDialog
import com.example.a6starter.ui.components.NoteCard
import com.example.a6starter.viewmodel.UiState
import kotlinx.coroutines.launch

@Composable
fun UserNotesScreen(viewModel: NotesViewModel, navController: NavController, userId: Int) {
    val notesState by viewModel.notesState.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(userId) {
        viewModel.fetchNotesForUser(userId)
    }

    var noteToEdit by remember { mutableStateOf<Note?>(null) }
    var noteToDelete by remember { mutableStateOf<Note?>(null) }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text("User's Notes") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (notesState) {
                is UiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is UiState.Success -> {
                    val notes = (notesState as UiState.Success<List<Note>>).data
                    if (notes.isEmpty()) {
                        Text(
                            text = "No notes available.",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            items(notes) { note ->
                                NoteCard(
                                    note = note,
                                    onEdit = { noteToEdit = note },
                                    onDelete = { noteToDelete = note }
                                )
                            }
                        }
                    }
                }
                is UiState.Error -> {
                    val message = (notesState as UiState.Error).message
                    Text(
                        text = message,
                        color = MaterialTheme.colors.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            if (noteToEdit != null) {
                EditNoteDialog(
                    note = noteToEdit!!,
                    onDismiss = { noteToEdit = null },
                    onConfirm = { updatedTitle, updatedContent ->
                        viewModel.updateNote(noteToEdit!!.id, updatedTitle, updatedContent)
                        noteToEdit = null
                        coroutineScope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Note updated successfully!")
                        }
                    }
                )
            }

            if (noteToDelete != null) {
                DeleteConfirmationDialog(
                    onConfirm = {
                        viewModel.deleteNote(noteToDelete!!.id)
                        noteToDelete = null
                        coroutineScope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Note deleted successfully!")
                        }
                    },
                    onDismiss = { noteToDelete = null }
                )
            }
        }
    }
}
