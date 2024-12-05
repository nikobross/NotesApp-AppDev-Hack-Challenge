package com.example.a6starter

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun CreateScreen(viewModel: NotesViewModel, navController: NavController) {
    var noteContent by remember { mutableStateOf("") }
    val currentUserId = 1 // Assuming the current user is Alice

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(innerPadding)
        ) {
            Text(
                text = "Create a New Note",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            OutlinedTextField(
                value = noteContent,
                onValueChange = { noteContent = it },
                label = { Text("Note Content") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (noteContent.isNotBlank()) {
                        viewModel.addNote(currentUserId, noteContent)
                        noteContent = ""
                        coroutineScope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Note saved!")
                        }
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = "Save Note")
            }
        }
    }
}