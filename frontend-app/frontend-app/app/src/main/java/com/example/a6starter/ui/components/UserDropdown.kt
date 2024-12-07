package com.example.a6starter.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.a6starter.model.User

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserDropdown(
    users: List<User>,
    selectedUser: User?,
    onUserSelected: (User) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var displayText by remember { mutableStateOf("Select User") }

    LaunchedEffect(selectedUser) {
        displayText = selectedUser?.username ?: "Select User"
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = displayText,
            onValueChange = {},
            readOnly = true,
            label = { Text("User") },
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = "Dropdown Arrow"
                )
            },
            modifier = Modifier
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            if (users.isEmpty()) {
                DropdownMenuItem(onClick = { /* Do nothing */ }) {
                    Text(text = "No users available")
                }
            } else {
                users.forEach { user ->
                    DropdownMenuItem(
                        onClick = {
                            onUserSelected(user)
                            expanded = false
                        }
                    ) {
                        Text(text = user.username)
                    }
                }
            }
        }
    }
}