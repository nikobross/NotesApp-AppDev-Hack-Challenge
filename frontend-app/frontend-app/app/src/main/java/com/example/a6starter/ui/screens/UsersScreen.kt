package com.example.a6starter.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.a6starter.Screen
import com.example.a6starter.model.User
import com.example.a6starter.viewmodel.NotesViewModel
import com.example.a6starter.ui.components.AddUserDialog
import com.example.a6starter.ui.components.UserCard
import com.example.a6starter.viewmodel.UiState

@Composable
fun UsersScreen(viewModel: NotesViewModel, navController: NavController) {
    val usersState by viewModel.usersState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var newUserName by remember { mutableStateOf("") }
    var newUserPassword by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Users") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add User")
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (usersState) {
                is UiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is UiState.Success -> {
                    val users = (usersState as UiState.Success<List<User>>).data
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        items(users) { user ->
                            UserCard(
                                user = user,
                                onDelete = { viewModel.deleteUser(user.id) },
                                onClick = {
                                    navController.navigate(Screen.UserNotes.createRoute(user.id))
                                }
                            )
                        }
                    }
                }
                is UiState.Error -> {
                    val message = (usersState as UiState.Error).message
                    Text(
                        text = message,
                        color = MaterialTheme.colors.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            if (showDialog) {
                AddUserDialog(
                    onAdd = { username, password ->
                        viewModel.createUser(username, password)
                        showDialog = false
                        newUserName = ""
                        newUserPassword = ""
                    },
                    onDismiss = { showDialog = false }
                )
            }
        }
    }
}
