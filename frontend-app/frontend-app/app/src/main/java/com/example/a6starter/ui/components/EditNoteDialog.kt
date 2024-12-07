package com.example.a6starter.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.a6starter.model.Note

@Composable
fun EditNoteDialog(note: Note, onDismiss: () -> Unit, onConfirm: (String, String) -> Unit) {
    var updatedTitle by remember { mutableStateOf(note.title) }
    var updatedContent by remember { mutableStateOf(note.content) }
    var isTitleError by remember { mutableStateOf(false) }
    var isContentError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Edit Note") },
        text = {
            Column {
                OutlinedTextField(
                    value = updatedTitle,
                    onValueChange = {
                        updatedTitle = it
                        isTitleError = it.isBlank()
                    },
                    label = { Text("Title") },
                    isError = isTitleError,
                    modifier = Modifier.fillMaxWidth()
                )
                if (isTitleError) {
                    Text(text = "Title cannot be empty", color = MaterialTheme.colors.error)
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = updatedContent,
                    onValueChange = {
                        updatedContent = it
                        isContentError = it.isBlank()
                    },
                    label = { Text("Content") },
                    isError = isContentError,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    maxLines = 10
                )
                if (isContentError) {
                    Text(text = "Content cannot be empty", color = MaterialTheme.colors.error)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (updatedTitle.isNotBlank() && updatedContent.isNotBlank()) {
                        onConfirm(updatedTitle, updatedContent)
                    }
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
