package com.example.a6starter.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun AddUserDialog(onAdd: (String, String) -> Unit, onDismiss: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isUsernameError by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Add New User") },
        text = {
            Column {
                OutlinedTextField(
                    value = username,
                    onValueChange = {
                        username = it
                        isUsernameError = it.isBlank()
                    },
                    label = { Text("Username") },
                    isError = isUsernameError,
                    modifier = Modifier.fillMaxWidth()
                )
                if (isUsernameError) {
                    Text(text = "Username cannot be empty", color = MaterialTheme.colors.error)
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        isPasswordError = it.isBlank()
                    },
                    label = { Text("Password") },
                    isError = isPasswordError,
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation()
                )
                if (isPasswordError) {
                    Text(text = "Password cannot be empty", color = MaterialTheme.colors.error)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (username.isNotBlank() && password.isNotBlank()) {
                        onAdd(username, password)
                    }
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
