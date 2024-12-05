package com.example.a6starter
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//
//
//@Composable
//fun UserNotesScreen(userId: Int, viewModel: NotesViewModel, navController: NavController = rememberNavController()) {
//    val users by viewModel.users.collectAsState()
//    val user = users.find { it.id == userId } ?: return
//
//    val notes = viewModel.getNotesForUser(userId)
//
//    val scaffoldState = rememberScaffoldState()
//
//    Scaffold(
//        scaffoldState = scaffoldState,
//        topBar = {
//            TopAppBar(
//                title = { Text(text = "${user.name}'s Notes") },
//                navigationIcon = {
//                    IconButton(onClick = { navController.popBackStack() }) {
//                        Icon(
//                            imageVector = Icons.Default.ArrowBack,
//                            contentDescription = "Go Back"
//                        )
//                    }
//                }
//            )
//        }
//    ) { innerPadding ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp)
//                .padding(innerPadding)
//        ) {
//            if (notes.isEmpty()) {
//                Text(text = "No notes found.", style = MaterialTheme.typography.body1)
//            } else {
//                LazyColumn {
//                    items(notes) { note ->
//                        Card(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(vertical = 4.dp),
//                            elevation = 2.dp
//                        ) {
//                            Row(modifier = Modifier.padding(16.dp)) {
//                                Text(text = note.content, style = MaterialTheme.typography.body1)
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}

// UserNotesScreen.kt

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun UserNotesScreen(userId: Int, viewModel: NotesViewModel, navController: NavController) {
    val users by viewModel.users.collectAsState()
    val user = users.find { it.id == userId } ?: return

    val notes = viewModel.getNotesForUser(userId)
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    // State for Edit Dialog
    var isEditDialogOpen by remember { mutableStateOf(false) }
    var noteToEdit by remember { mutableStateOf<Note?>(null) }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "${user.name}'s Notes") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Go Back"
                        )
                    }
                },
                actions = {
                    // Optional: Add actions here if needed
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(innerPadding)
        ) {
            if (notes.isEmpty()) {
                Text(
                    text = "No notes found.",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn {
                    items(notes) { note ->
                        NoteItem(
                            note = note,
                            onDelete = {
                                coroutineScope.launch {
                                    viewModel.deleteNote(note.id)
                                    scaffoldState.snackbarHostState.showSnackbar("Note deleted")
                                }
                            },
                            onEdit = {
                                noteToEdit = note
                                isEditDialogOpen = true
                            }
                        )
                    }
                }
            }
        }

        // Edit Note Dialog
        if (isEditDialogOpen && noteToEdit != null) {
            EditNoteDialog(
                note = noteToEdit!!,
                onDismiss = { isEditDialogOpen = false },
                onSave = { updatedContent ->
                    viewModel.updateNote(noteToEdit!!.id, updatedContent)
                    isEditDialogOpen = false
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("Note updated")
                    }
                }
            )
        }
    }
}

@Composable
fun NoteItem(note: Note, onDelete: () -> Unit, onEdit: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Note Content
            Text(
                text = note.content,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            // Edit Button
            IconButton(onClick = onEdit) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Note",
                    tint = MaterialTheme.colors.primary
                )
            }
            // Delete Button
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Note",
                    tint = MaterialTheme.colors.error
                )
            }
        }
    }
}

@Composable
fun EditNoteDialog(note: Note, onDismiss: () -> Unit, onSave: (String) -> Unit) {
    var updatedContent by remember { mutableStateOf(note.content) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Edit Note") },
        text = {
            OutlinedTextField(
                value = updatedContent,
                onValueChange = { updatedContent = it },
                label = { Text("Note Content") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 5
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (updatedContent.isNotBlank()) {
                        onSave(updatedContent)
                    }
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
