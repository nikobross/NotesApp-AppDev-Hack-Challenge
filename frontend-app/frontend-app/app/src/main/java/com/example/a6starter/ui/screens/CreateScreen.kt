package com.example.a6starter.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.a6starter.model.User
import com.example.a6starter.viewmodel.NotesViewModel
import com.example.a6starter.ui.components.UserDropdown
import com.example.a6starter.viewmodel.UiState
import kotlinx.coroutines.launch

@Composable
fun CreateScreen(viewModel: NotesViewModel, navController: NavController) {
    var noteContent by remember { mutableStateOf("") }
    var noteTitle by remember { mutableStateOf("") }
    val usersState by viewModel.usersState.collectAsState()
    var selectedUser by remember { mutableStateOf<User?>(null) }
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text("Create Note") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
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

            when (usersState) {
                is UiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                is UiState.Success -> {
                    val users = (usersState as UiState.Success<List<User>>).data
                    UserDropdown(
                        users = users,
                        selectedUser = selectedUser,
                        onUserSelected = { user ->
                            selectedUser = user
                        }
                    )
                }
                is UiState.Error -> {
                    val message = (usersState as UiState.Error).message
                    Text(
                        text = message,
                        color = MaterialTheme.colors.error,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = noteTitle,
                onValueChange = { noteTitle = it },
                label = { Text("Note Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = noteContent,
                onValueChange = { noteContent = it },
                label = { Text("Note Content") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                maxLines = 10
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (noteTitle.isNotBlank() && noteContent.isNotBlank() && selectedUser != null) {
                        viewModel.createNote(
                            title = noteTitle,
                            content = noteContent,
                            userId = selectedUser!!.id
                        )
                        coroutineScope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Note saved!")
                        }
                        noteTitle = ""
                        noteContent = ""
                    } else {
                        coroutineScope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Please fill all fields and select a user.")
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
